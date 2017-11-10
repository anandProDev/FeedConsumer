package com.transformer;

import com.domain.BaseEvent;
import com.model.Event;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
public class BaseEventTransformerImpl implements BaseEventTransformer {

    @Override
    public BaseEvent transform(Event event) {

        return BaseEvent.BaseEventBuilder.aBaseEvent().withHeader(event.getHeader())
                .withEventId(event.getEventId())
                .withCategory(event.getCategory())
                .withSubCategory(event.getSubCategory())
                .withName(event.getName())
                .withStartTime(event.getStartTime())
                .withDisplayed(event.isDisplayed())
                .withSuspended(event.isSuspended())
                .withMarkets(new ArrayList<>())
                .build();
    }

}

