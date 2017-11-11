package com.message;

import com.exception.FeedReceiverException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FeedReceiverImpl implements FeedReceiver {

    private static final Logger LOGGER = LogManager.getLogger(FeedReceiverImpl.class);
    EventFeedReceiver eventFeedReceiver;
    ConnectionFactory connectionFactory;
    private final String queue;

    @Autowired
    public FeedReceiverImpl(EventFeedReceiver eventFeedReceiver,
                            ConnectionFactory connectionFactory,
                            @Value ("${amqp.internal.message.queue}") String queue) {
        this.eventFeedReceiver = eventFeedReceiver;
        this.connectionFactory = connectionFactory;
        this.queue = queue;
    }

    @Override
    public void receiveFeeds() {

        try {
            final Channel channel = getChannel();
            final Consumer consumer = eventFeedReceiver.getDefaultConsumer(channel);
            channel.basicQos(10);
            boolean autoAck = true;
            channel.basicConsume(queue, autoAck, consumer);
        } catch (IOException e) {
            LOGGER.error("Could not read event messages", e);
            throw new FeedReceiverException("Could not read event messages", e);
        }
    }

    protected Channel getChannel() {
        final Connection connection = connectionFactory.createConnection();
        return connection.createChannel(false);
    }
}
