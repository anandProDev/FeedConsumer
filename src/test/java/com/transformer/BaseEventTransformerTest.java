package com.transformer;

import com.domain.BaseEvent;
import com.model.Event;
import com.model.Header;
import com.factory.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class BaseEventTransformerTest {

    public static final String EVENT_ID = "1";
    public static final String START_TIME = "start time";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub category";

    Event event;

    @Mock
    Header header;

    BaseEventTransformer baseEventTransformer;

    @Before
    public void setUp(){

        event = Event.EventBuilder.anEvent().withCategory("testCategory")
                .withHeader(header)
                .withEventId(EVENT_ID)
                .withStartTime(START_TIME)
                .withName(NAME)
                .withCategory(CATEGORY)
                .withSubCategory(SUB_CATEGORY)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.TRUE)
                .build();

        baseEventTransformer = new BaseEventTransformerImpl();
    }

    @Test
    public void transform_successful() throws Exception {

        BaseEvent baseEvent = baseEventTransformer.transform(event).get();

        assertThat(header, is(baseEvent.getHeader()));
        assertThat(EVENT_ID, is(baseEvent.getEventId()));
        assertThat(START_TIME, is(baseEvent.getStartTime()));
        assertThat(NAME, is(baseEvent.getName()));
        assertThat(CATEGORY, is(baseEvent.getCategory()));
        assertThat(SUB_CATEGORY, is(baseEvent.getSubCategory()));
        assertThat(Boolean.TRUE, is(baseEvent.isDisplayed()));
        assertThat(Boolean.TRUE, is(baseEvent.isSuspended()));

        assertTrue(baseEvent.getMarkets().isEmpty());
    }

    @Test
    public void error_transforming_event() throws Exception {

        Optional<BaseEvent> event = baseEventTransformer.transform(null);
        assertFalse(event.isPresent());
    }
}