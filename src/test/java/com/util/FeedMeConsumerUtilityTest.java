package com.util;

import com.couchbase.client.java.document.JsonDocument;
import com.domain.BaseEvent;
import com.model.Header;
import com.service.UnitTest;
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
public class FeedMeConsumerUtilityTest {

    public static final String EVENT_ID = "1";
    public static final String START_TIME = "start time";
    public static final String NAME = "name";
    public static final String CATEGORY = "category";
    public static final String SUB_CATEGORY = "sub category";
    public static final String TEST_CATEGORY = "testCategory";

    BaseEvent baseEvent;

    Header header;

    public static final int MSG_ID = 1;
    public static final String OPERATION = "create";
    public static final String TYPE = "event";
    public static final String TIMESTAMP = "1509874975700";

    @Before
    public void setUp() throws Exception {
        header = Header.HeaderBuilder.aHeader().withMsgId(MSG_ID).withOperation(OPERATION).withType(TYPE).withTimestamp(TIMESTAMP).build();

        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent()
                .withCategory(TEST_CATEGORY)
                .withEventId(EVENT_ID)
                .withStartTime(START_TIME)
                .withName(NAME)
                .withCategory(CATEGORY)
                .withSubCategory(SUB_CATEGORY)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.TRUE)
                .withHeader(header)
                .build();
    }

    @Test
    public void transform_successful() throws Exception {

        Optional<JsonDocument> jsonDocument = FeedMeConsumerUtility.transform(baseEvent);

        assertThat(jsonDocument.get().id(), is(EVENT_ID));
        assertThat(jsonDocument.get().content().toString(), is("{\"displayed\":true,\"eventId\":\"1\",\"subCategory\":\"sub category\",\"markets\":[],\"name\":\"name\",\"header\":{\"msgId\":1,\"type\":\"event\",\"operation\":\"create\",\"timestamp\":\"1509874975700\"},\"startTime\":\"start time\",\"category\":\"category\",\"suspended\":true}"));
    }

    @Test
    public void transformationReturnsEmpty_whenExceptionIsThrown() throws Exception {

        Optional<JsonDocument> jsonDocument = FeedMeConsumerUtility.transform(null);
        assertFalse(jsonDocument.isPresent());
    }
}