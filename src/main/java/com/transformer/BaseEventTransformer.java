package com.transformer;


import com.domain.BaseEvent;
import com.model.Event;

public interface BaseEventTransformer {

    BaseEvent transform(Event event);
}
