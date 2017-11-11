package com.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;

public interface EventFeedReceiver {

    DefaultConsumer getDefaultConsumer(final Channel channel);
}
