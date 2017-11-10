package com.domain;

import com.model.Header;
import com.service.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class BaseEventTest {

    public static final String EVENT_ID = "1";
    public static final String START_TIME = "start time";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub category";
    public static final String TEST_CATEGORY = "testCategory";

    @Mock
    Header header;

    @Mock
    private List<BaseMarket> markets;

    BaseEvent baseEvent;


    @Before
    public void setUp(){
        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent()
                .withCategory(TEST_CATEGORY)
                .withHeader(header)
                .withEventId(EVENT_ID)
                .withStartTime(START_TIME)
                .withName(NAME)
                .withCategory(CATEGORY)
                .withSubCategory(SUB_CATEGORY)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.TRUE)
                .withMarkets(markets)
                .build();
    }

    @Test
    public void buildBaseEvent_successful() throws Exception {
        assertThat(header, is(baseEvent.getHeader()));
        assertThat(EVENT_ID, is(baseEvent.getEventId()));
        assertThat(START_TIME, is(baseEvent.getStartTime()));
        assertThat(NAME, is(baseEvent.getName()));
        assertThat(CATEGORY, is(baseEvent.getCategory()));
        assertThat(SUB_CATEGORY, is(baseEvent.getSubCategory()));
        assertThat(Boolean.TRUE, is(baseEvent.isDisplayed()));
        assertThat(Boolean.TRUE, is(baseEvent.isSuspended()));
        assertThat(markets, is(baseEvent.getMarkets()));
    }

    @Test
    public void toString_ReturnsExpectedValue() throws Exception {

        assertThat(baseEvent.toString(), is("BaseEvent{header=header, eventId='1', category='category', subCategory='sub category', name='name', startTime='start time', displayed=true, suspended=true, markets=markets}"));

    }
}