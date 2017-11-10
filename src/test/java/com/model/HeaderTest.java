package com.model;

import com.service.UnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class HeaderTest {
    public static final int MSG_ID = 1;
    public static final String OPERATION = "create";
    public static final String TYPE = "event";
    public static final String TIMESTAMP = "1509874975700";
    Header header;
    @Before
    public void setUp(){

        header = Header.HeaderBuilder.aHeader().withMsgId(MSG_ID).withOperation(OPERATION).withType(TYPE).withTimestamp(TIMESTAMP).build();
    }

    @Test
    public void header_is_built_with_correct_values() throws Exception {

        assertThat(MSG_ID, is(header.getMsgId()));
        assertThat(TYPE, is(header.getType()));
        assertThat(OPERATION, is(header.getOperation()));
        assertThat(TIMESTAMP, is(header.getTimestamp()));
    }

    @Test
    public void toString_ReturnsExpectedValue() throws Exception {

        Assert.assertThat(header.toString(), is("{msgId=1, operation='create', type='event', timestamp='1509874975700'}"));

    }
}