package com.transformer;


import com.domain.BaseEvent;
import com.model.Event;

import java.util.Optional;

public interface BaseEventTransformer {

    Optional<BaseEvent> transform(Event event);
}
