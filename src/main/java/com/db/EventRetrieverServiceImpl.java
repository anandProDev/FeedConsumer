package com.db;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.domain.BaseEvent;
import com.message.EventFeedReceiverImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventRetrieverServiceImpl implements EventRetrieverService {

    private static final Logger LOGGER = LogManager.getLogger(EventFeedReceiverImpl.class);
    private final RepositoryService repositoryService;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public EventRetrieverServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public Optional<BaseEvent> getBaseEvent(String eventId) {

        try {
            JsonDocument document = repositoryService.getDocument(eventId);
            return Optional.of(mapper.readValue(document.content().toString(), BaseEvent.class));
        } catch (Exception e) {
            LOGGER.error("Could not retrieve document from couchbase for event : " + eventId);
            return Optional.empty();
        }
    }
}
