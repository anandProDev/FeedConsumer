package com.service;

import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.model.Event;
import com.transformer.BaseEventTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class EventFeedHandlerTest {

    public static final String EVENT_ID = "eventId";

    EventFeedHandler eventFeedHandler;

    @Mock
    BaseEventTransformer baseEventTransformer;

    @Mock
    RepositoryService repositoryService;

    @Mock
    Event event;

    BaseEvent baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent().withEventId(EVENT_ID).build();

    Optional<BaseEvent> baseEventOptional;

    @Before
    public void setUp(){

        baseEventOptional = Optional.of(baseEvent);
        eventFeedHandler = new EventFeedHandlerImpl(baseEventTransformer, repositoryService);
    }

    @Test
    public void handleEvent_successful() throws Exception {

        when(baseEventTransformer.transform(event)).thenReturn(baseEventOptional);

        eventFeedHandler.handle(event);

        verify(repositoryService, times(1)).insertDocument(any());
    }

}