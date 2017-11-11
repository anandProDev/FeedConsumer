package com.transformer;

import com.domain.BaseEvent;
import com.model.Event;
import com.service.MarketHandlerFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;


@Component
public class BaseEventTransformerImpl implements BaseEventTransformer {

    private static final Logger LOGGER = LogManager.getLogger(BaseEventTransformerImpl.class);

    @Override
    public Optional<BaseEvent> transform(Event event) {
        try {

            return Optional.of(BaseEvent.BaseEventBuilder.aBaseEvent().withHeader(event.getHeader())
                    .withEventId(event.getEventId())
                    .withCategory(event.getCategory())
                    .withSubCategory(event.getSubCategory())
                    .withName(event.getName())
                    .withStartTime(event.getStartTime())
                    .withDisplayed(event.isDisplayed())
                    .withSuspended(event.isSuspended())
                    .withMarkets(new ArrayList<>())
                    .build());

        } catch (Exception e) {
            LOGGER.error("Unable to transform Event to BaseEvent " + event, e);
            return Optional.empty();
        }
    }

}

