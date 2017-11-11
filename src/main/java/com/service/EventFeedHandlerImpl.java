package com.service;

import com.couchbase.client.java.document.JsonDocument;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.model.Event;
import com.transformer.BaseEventTransformer;
import com.util.FeedMeConsumerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventFeedHandlerImpl implements EventFeedHandler {

    private final BaseEventTransformer baseEventTransformer;
    private final RepositoryService repositoryService;

    @Autowired
    public EventFeedHandlerImpl(BaseEventTransformer baseEventTransformer, RepositoryService repositoryService) {
        this.baseEventTransformer = baseEventTransformer;
        this.repositoryService = repositoryService;
    }

    @Override
    public void handle(Event event) {
        BaseEvent baseEvent = baseEventTransformer.transform(event);
        FeedMeConsumerUtility.transform(baseEvent)
                .ifPresent( document -> repositoryService.insertDocument(document));
    }
}
