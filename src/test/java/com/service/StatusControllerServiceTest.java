package com.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class StatusControllerServiceTest {


    @Mock
    FeedMeClient feedMeClient;
    String applicationName = "Test";
    StatusControllerService statusControllerService;

    @Before
    public void setUp() throws Exception {

        when(feedMeClient.getStatus()).thenReturn(" *** OK from Consumer ***");
        statusControllerService = new StatusControllerService(feedMeClient, applicationName);
    }

    @Test
    public void status() throws Exception {

        String status = statusControllerService.status();
        assertThat(status, is(" *** OK from Test *** "));
    }

}