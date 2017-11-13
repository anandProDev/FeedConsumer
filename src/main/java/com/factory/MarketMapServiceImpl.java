package com.factory;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.db.RepositoryService;
import com.util.FeedMeConsumerUtility;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class MarketMapServiceImpl implements MarketMapService {

    private static final Logger LOGGER = LogManager.getLogger(MarketMapServiceImpl.class);
    public static final String MARKET_EVENT_MAP = "marketEventMap";
    private RepositoryService repositoryService;
    protected ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public MarketMapServiceImpl(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Override
    public Optional<Map<String, String>> load() {
        try {
            JsonDocument document = repositoryService.getDocument(MARKET_EVENT_MAP);
            return Optional.of(mapper.readValue(document.content().toString(), HashMap.class));
        } catch (Exception e) {
            LOGGER.error("Could not load marketEventMap", e);
            return Optional.empty();
        }
    }

    @Override
    public void save(Map<String, String> marketEventHolder) {

        Optional<JsonDocument> doc =
                FeedMeConsumerUtility.transform(MARKET_EVENT_MAP, marketEventHolder);

        JsonDocument document = repositoryService.getDocument(MARKET_EVENT_MAP);


        if(document == null)
            repositoryService.insertDocument(doc.get());
        else
            repositoryService.updateDocument(doc.get());
    }
}
