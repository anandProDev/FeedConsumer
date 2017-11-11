package com.service;

import com.model.Event;

public interface EventHandler {

    void handle(Event event);
}
