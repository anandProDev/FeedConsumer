package com.exception;

import com.service.UnitTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class FeedReceiverExceptionTest {


    public static final String TEST_MESSAGE = "testMessage";

    @Test
    public void FeedReceiverException() throws Exception {

        FeedReceiverException feedReceiverException = new FeedReceiverException(TEST_MESSAGE, new Exception());
        assertThat(feedReceiverException.getMessage(), is(TEST_MESSAGE));


    }
}