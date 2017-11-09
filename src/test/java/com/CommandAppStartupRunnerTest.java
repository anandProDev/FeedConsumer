package com;

import com.service.FeedReceiver;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class CommandAppStartupRunnerTest {

    CommandAppStartupRunner commandAppStartupRunner;

    @Mock
    FeedReceiver feedReceiver;

    @Before
    public void setUp() throws Exception {

        commandAppStartupRunner = new CommandAppStartupRunner(feedReceiver);
    }

    @Test
    public void feedProcessorInvoked() throws Exception {

        commandAppStartupRunner.run();
        verify(feedReceiver, times(1)).receiveFeeds();
    }
}