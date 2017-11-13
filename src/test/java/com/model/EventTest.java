package com.model;


import com.factory.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class EventTest {

    public static final String EVENT_ID = "1";
    public static final String START_TIME = "start time";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub category";

    @Mock
    Header header;

    Event event;

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
    }

    @Test
    public void Event_is_build_with_expected_values() throws Exception {

        assertThat(header, is(event.getHeader()));
        assertThat(EVENT_ID, is(event.getEventId()));
        assertThat(START_TIME, is(event.getStartTime()));
        assertThat(NAME, is(event.getName()));
        assertThat(CATEGORY, is(event.getCategory()));
        assertThat(SUB_CATEGORY, is(event.getSubCategory()));
        assertThat(Boolean.TRUE, is(event.isDisplayed()));
        assertThat(Boolean.TRUE, is(event.isSuspended()));
    }

    @Test
    public void toString_ReturnsExpectedValue() throws Exception {

        assertThat(event.toString(), is("Event{header=header, eventId='1', category='category', subCategory='sub category', name='name', startTime='start time', displayed=true, suspended=true}"));

    }
}