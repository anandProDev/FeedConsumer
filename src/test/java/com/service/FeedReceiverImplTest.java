package com.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import static org.junit.Assert.*;


public class FeedReceiverImplTest {

    FeedReceiverImpl feedReceiver;

    @Mock
    ConnectionFactory connectionFactory;

    EventFeedReceiver eventFeedReceiver;

    @Before
    public void setUp() throws Exception {
        //feedReceiver = new FeedReceiverImpl(connectionFactory);
    }

    @Test
    public void receiveFeeds() throws Exception {
        feedReceiver.receiveFeeds();
    }

    @Test
    public void getDefaultConsumer() throws Exception {

    }

}