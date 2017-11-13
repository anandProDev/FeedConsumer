package com.message;

import com.exception.FeedReceiverException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.factory.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class FeedReceiverImplTest {

    FeedReceiverImpl feedReceiver;

    @Mock
    EventFeedReceiver eventFeedReceiver;

    @Mock
    ConnectionFactory connectionFactory;

    @Mock
    Connection connection;

    @Mock
    Channel channel;

    @Mock
    DefaultConsumer consumer;

    String queue = "test";

    @Before
    public void setUp() throws Exception {

        when(eventFeedReceiver.getDefaultConsumer(channel)).thenReturn(consumer);
        when(connectionFactory.createConnection()).thenReturn(connection);
        when(connection.createChannel(false)).thenReturn(channel);

        feedReceiver = new FeedReceiverImpl(eventFeedReceiver, connectionFactory, queue);
    }

    @Test
    public void receiveFeeds() throws Exception {

        feedReceiver.receiveFeeds();

        verify(eventFeedReceiver, times(1)).getDefaultConsumer(channel);
        verify(channel, times(1)).basicConsume("test", true, consumer);
    }

    @Test(expected = FeedReceiverException.class)
    public void exceptionWhenReadingFeeds() throws Exception {

        when(eventFeedReceiver.getDefaultConsumer(channel)).thenThrow(IOException.class);

        feedReceiver.receiveFeeds();

        verify(eventFeedReceiver, times(1)).getDefaultConsumer(channel);
        verify(channel, times(1)).basicConsume("test", true, consumer);
    }

    @Test
    public void getChannel() throws Exception {

        Channel channel = feedReceiver.getChannel();

        assertThat(channel, is(this.channel));

    }
}