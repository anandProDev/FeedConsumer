package com.service;

import com.db.RepositoryService;
import com.model.Event;
import com.transformer.BaseEventTransformer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.util.FeedMeConsumerUtility.transform;

@Component
public class EventFeedHandlerImpl implements EventFeedHandler {

    private static final Logger LOGGER = LogManager.getLogger(EventFeedHandlerImpl.class);
    private final BaseEventTransformer baseEventTransformer;
    private final RepositoryService repositoryService;

    @Autowired
    public EventFeedHandlerImpl(BaseEventTransformer baseEventTransformer, RepositoryService repositoryService) {
        this.baseEventTransformer = baseEventTransformer;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Event event) {
        LOGGER.debug("processing event feed with id: " + event.getEventId());
        baseEventTransformer.transform(event)
                .ifPresent( baseEvent -> {
                        transform(baseEvent)
                            .ifPresent( document -> repositoryService.insertDocument(document));
        });
    }
}
