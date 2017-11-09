package com.service;

import com.couchbase.CouchbaseRepositoryService;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.model.Event;
import com.model.Feed;
import com.model.FeedHolder;
import com.model.FeedType;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class EventFeedReceiverImpl implements EventFeedReceiver{

    public static final String EVENT = "EventFeed";
    private final Gson gson;
    private final CouchbaseRepositoryService cb;
    private static final Logger LOGGER = LogManager.getLogger(EventFeedReceiverImpl.class);
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public EventFeedReceiverImpl(Gson gson, CouchbaseRepositoryService cb) {
        this.gson = gson;
        this.cb = cb;
    }

    @Override
    public void receiveEventFeeds(Channel channel) {

        boolean autoAck = false;
        new Thread(){
          public void run(){
              try{
                channel.basicQos(10);
                channel.basicConsume(EVENT, autoAck, getDefaultConsumer(channel));
              } catch (Exception e){
                  LOGGER.error("Error processing event feeds");
              }
          }
        }.start();
    }
    protected DefaultConsumer getDefaultConsumer(final Channel channel) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                FeedHolder feedHolder = gson.fromJson(message, FeedHolder.class);

                FeedType type = feedHolder.getType();

                feedHolder.getFeed();


                Event event = gson.fromJson(message, Event.class);
                JsonObject jsonObject = JsonObject.fromJson(message);

                JsonDocument document = JsonDocument.create(event.getEventId(), jsonObject);
                cb.upsertDocument(document);
            }
        };
    }
}
