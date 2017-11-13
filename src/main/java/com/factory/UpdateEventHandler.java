package com.factory;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.model.Event;
import com.transformer.BaseEventTransformer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.util.FeedMeConsumerUtility.transformToJsonDoc;

@Component
public class UpdateEventHandler implements EventHandler {

    private static final Logger LOGGER = LogManager.getLogger(UpdateEventHandler.class);
    private final BaseEventTransformer baseEventTransformer;
    private final RepositoryService repositoryService;
    private final EventRetrieverService eventRetrieverService;

    @Autowired
    public UpdateEventHandler(BaseEventTransformer baseEventTransformer,
                              RepositoryService repositoryService,
                              EventRetrieverService eventRetrieverService) {
        this.baseEventTransformer = baseEventTransformer;
        this.repositoryService = repositoryService;
        this.eventRetrieverService = eventRetrieverService;
    }

    @Override
    public void handle(Event event) {
        LOGGER.debug("Processing update event feed with id: " + event.getEventId());

        baseEventTransformer.transform(event)
                .ifPresent( baseEvent -> {
                    eventRetrieverService.getBaseEvent(event.getEventId())
                            .ifPresent(baseEventCB -> {
                                baseEvent.getMarkets().addAll(baseEventCB.getMarkets());
                                    transformToJsonDoc(baseEvent)
                                        .ifPresent( document -> repositoryService.updateDocument(document));
                    });
                });
    }
}
