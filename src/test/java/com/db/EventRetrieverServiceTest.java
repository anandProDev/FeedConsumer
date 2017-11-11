package com.db;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.domain.BaseEvent;
import com.service.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class EventRetrieverServiceTest {

    public static final String EVENT_ID = "e25adec3-b12c-4511-b1b6-26728eaa08bc";
    EventRetrieverService eventRetrieverService;

    @Mock
    RepositoryService repositoryService;

    JsonDocument jsonDocument;
    JsonObject jsonObject;

    ObjectMapper objectMapper = new ObjectMapper();

    Map<String, Object> map =  new HashMap<>();

    @Before
    public void setUp() throws Exception {

        map.put("eventId", EVENT_ID);
        String eventString = objectMapper.writeValueAsString(map);

        jsonObject = JsonObject.fromJson(eventString);

        jsonDocument = JsonDocument.create(EVENT_ID, jsonObject);
        eventRetrieverService = new EventRetrieverServiceImpl(repositoryService);
    }

    @Test
    public void getBaseEvent() throws Exception {

        when(repositoryService.getDocument(EVENT_ID)).thenReturn(jsonDocument);

        Optional<BaseEvent> baseEvent = eventRetrieverService.getBaseEvent(EVENT_ID);

        assertThat(baseEvent.get().getEventId(), is(EVENT_ID));
    }

    @Test
    public void getEvent_throwsException() throws Exception {
        when(repositoryService.getDocument(EVENT_ID)).thenReturn(null);

        Optional<BaseEvent> baseEvent = eventRetrieverService.getBaseEvent(EVENT_ID);

        assertFalse(baseEvent.isPresent());
    }
}