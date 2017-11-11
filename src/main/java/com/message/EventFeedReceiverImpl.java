package com.message;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;
import com.model.Event;
import com.model.FeedType;
import com.model.Market;
import com.model.Outcome;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.service.EventHandlerFactory;
import com.service.MarketHandlerFactory;
import com.service.OutcomeHandlerFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventFeedReceiverImpl implements EventFeedReceiver{

    private final EventHandlerFactory eventHandlerFactory;
    private final MarketHandlerFactory marketHandlerFactory;
    private final OutcomeHandlerFactory outcomeHandlerFactory;

    protected ObjectMapper mapper = new ObjectMapper();
    protected Map<String, String> marketEventMap = new HashMap<>();

    private static final Logger LOGGER = LogManager.getLogger(EventFeedReceiverImpl.class);
    @Autowired
    public EventFeedReceiverImpl(EventHandlerFactory eventHandlerFactory,
                                 MarketHandlerFactory marketHandlerFactory,
                                 OutcomeHandlerFactory outcomeHandlerFactory) {
        this.eventHandlerFactory = eventHandlerFactory;
        this.marketHandlerFactory = marketHandlerFactory;
        this.outcomeHandlerFactory = outcomeHandlerFactory;
    }

    @Override
    public DefaultConsumer getDefaultConsumer(final Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                String type = JsonPath.from(message).get("header.type");
                String operation = JsonPath.from(message).get("header.operation");

                handleMessages(message, type, operation);
            }
        };
    }

    protected void handleMessages(String message, String type, String operation) throws IOException {

        if(type.equalsIgnoreCase(FeedType.EVENT.getName())){
            Event event = mapper.readValue(message, Event.class);
            eventHandlerFactory.getHandler(operation)
                    .ifPresent( handler -> handler.handle(event));
        }
        else if(type.equalsIgnoreCase(FeedType.MARKET.getName())){
            Market market = mapper.readValue(message, Market.class);
            marketEventMap.put(market.getMarketId(), market.getEventId());
            marketHandlerFactory.getHandler(operation)
                    .ifPresent( handler -> handler.handle(market));
        }
        else if(type.equalsIgnoreCase(FeedType.OUTCOME.getName())){
            Outcome outcome = mapper.readValue(message, Outcome.class);
            String eventId = marketEventMap.get(outcome.getMarketId());
            outcomeHandlerFactory.getHandler(operation).
                    ifPresent(handler -> handler.handle(outcome, eventId));
        }
    }
}