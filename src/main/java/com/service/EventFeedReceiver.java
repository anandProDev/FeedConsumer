package com.service;

import com.rabbitmq.client.Channel;

public interface EventFeedReceiver {

    void receiveEventFeeds(Channel channel);
}
