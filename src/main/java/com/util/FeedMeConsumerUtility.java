package com.util;


import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.domain.BaseEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Optional;

public class FeedMeConsumerUtility {

    private static final Logger LOGGER = LogManager.getLogger(FeedMeConsumerUtility.class);
    public static ObjectMapper mapper = new ObjectMapper();

    public static Optional<JsonDocument> transformToJsonDoc(BaseEvent baseEvent) {

        JsonObject jsonObject = null;
        try {
            String jsonStr = mapper.writeValueAsString(baseEvent);
            jsonObject = JsonObject.fromJson(jsonStr);
            return Optional.of(JsonDocument.create(baseEvent.getEventId(), jsonObject));
        } catch (Exception e) {
            LOGGER.error("Unable to transformToJsonDoc baseEvent to JsonDoc "+ baseEvent , e);
            return Optional.empty();
        }
    }
}
