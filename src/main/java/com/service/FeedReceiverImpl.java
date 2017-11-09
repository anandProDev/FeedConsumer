package com.service;

import com.couchbase.CouchbaseRepositoryService;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.google.gson.Gson;
import com.jayway.restassured.path.json.JsonPath;
import com.model.*;
import com.rabbitmq.client.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javassist.runtime.Desc.getType;
import static rx.schedulers.Schedulers.test;

@Component
public class FeedReceiverImpl implements FeedReceiver {

    private static final Logger LOGGER = LogManager.getLogger(FeedReceiverImpl.class);
    public static final String EVENT = "EventFeed";
    private ConnectionFactory connectionFactory;
    private final Gson gson;
    private final CouchbaseRepositoryService couchbaseRepositoryService;
//    private final MarketFeedReceiver marketFeedReceiver;
//    private final OutcomeFeedReceiver outcomeFeedReceiver;
    BaseEvent baseEvent = new BaseEvent();
    Map<String, String> marketEventMap = new HashMap<>();

    @Autowired
    public FeedReceiverImpl(ConnectionFactory connectionFactory, Gson gson,
                            CouchbaseRepositoryService couchbaseRepositoryService) {
        this.connectionFactory = connectionFactory;
        this.gson = gson;
        this.couchbaseRepositoryService = couchbaseRepositoryService;
    }

    @Override
    public void receiveFeeds() {

        try {
            final Channel channel = getChannel();
            final Consumer consumer = getDefaultConsumer(channel);

            //eventFeedReceiver.receiveEventFeeds(channel);

            boolean autoAck = false;
            channel.basicConsume(EVENT, autoAck, consumer);
        } catch (IOException e) {
            LOGGER.error(" Could not read event messages", e);
        }
    }

    protected Channel getChannel() {
        final Connection connection = connectionFactory.createConnection();
        return connection.createChannel(false);
    }

    protected DefaultConsumer getDefaultConsumer(final Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");


                String type = JsonPath.from(message).get("header.type");
                String operation = JsonPath.from(message).get("header.operation");

                System.out.println(type + "  " + operation);

                if(type.equalsIgnoreCase(FeedType.EVENT.getName())){
                    Event event = gson.fromJson(message, Event.class);
                    baseEvent = transform(event);
                    buildDoc(baseEvent, event, FeedType.EVENT, message, operation);
                }
                else if(type.equalsIgnoreCase(FeedType.MARKET.getName())){

                    Market market = gson.fromJson(message, Market.class);
                    buildDoc(baseEvent, market, FeedType.MARKET, message, operation);
                }
                else if(type.equalsIgnoreCase(FeedType.OUTCOME.getName())){

                    Outcome outcome = gson.fromJson(message, Outcome.class);
                    buildDoc(baseEvent, outcome, FeedType.OUTCOME, message, operation);
                }
            }
        };
    }

    private void buildDoc(BaseEvent baseEvent, Feed feed, FeedType feedType, String operation,  String message) {

        if(feedType.getName().equalsIgnoreCase("event")){

            if(!baseEvent.getMarkets().isEmpty()){
                saveToCb(baseEvent);
            }

        } else if(feedType.getName().equalsIgnoreCase("market")){

            Market market = (Market)feed;
            marketEventMap.put(market.getMarketId(), market.getEventId());
            baseEvent.getMarkets().add(transform(market));
        }
        else if(feedType.getName().equalsIgnoreCase("outcome")){
            Outcome outcome = (Outcome) feed;
            if("create".equalsIgnoreCase(operation)){
                BaseMarket baseMarket = baseEvent.getMarkets().stream()
                        .filter(market -> outcome.getMarketId().equalsIgnoreCase(market.getMarketId()))
                        .findAny().orElse(null);
                if(baseMarket != null)
                    baseMarket.getOutcomes().add(outcome);

            }else if("update".equalsIgnoreCase(operation)){

                // get event doc from cb
                // get market
                // add outcome to market
                // update cb document
                System.out.println("hi");
            }

        }


    }

    private BaseEvent transform(Event feed) {

        return BaseEvent.BaseEventBuilder.aBaseEvent().withHeader(feed.getHeader())
                .withEventId(feed.getEventId())
                .build();

    }

    private BaseMarket transform(Market market) {
        return BaseMarket.BaseMarketBuilder.aBaseMarket().withHeader(market.getHeader())
                .withEventId(market.getEventId()).build();
    }


    private void saveToCb(BaseEvent baseEvent) {

        if(baseEvent.getMarkets().isEmpty())
            return;
        JsonObject jsonObject = JsonObject.fromJson(baseEvent.toString());

        JsonDocument document = JsonDocument.create(baseEvent.getEventId(), jsonObject);
        couchbaseRepositoryService.upsertDocument(document);
    }
}
