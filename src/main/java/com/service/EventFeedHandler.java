package com.service;

import com.model.Event;

public interface EventFeedHandler {

    void handle(Event event);
}
