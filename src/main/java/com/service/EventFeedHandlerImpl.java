package com.service;

import com.db.RepositoryService;
import com.model.Event;
import com.transformer.BaseEventTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.util.FeedMeConsumerUtility.transform;

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
        baseEventTransformer.transform(event)
                .ifPresent( baseEvent -> {
                        transform(baseEvent)
                            .ifPresent( document -> repositoryService.insertDocument(document));
        });
    }
}
