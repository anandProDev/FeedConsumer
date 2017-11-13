package com.service;

import com.factory.FeedMeClient;
import com.factory.UnitTest;
import com.service.StatusControllerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    public void application_isHealthy() throws Exception {

        String status = statusControllerService.status();
        assertThat(status, is("*** OK from Test\n   *** OK from Consumer ***"));
    }

    @Test
    public void upstreamSystemError() throws Exception {

        when(feedMeClient.getStatus()).thenThrow(NotFoundException.class);

        String status = statusControllerService.status();
        assertThat(status, is("*** OK from Test\n  Status of feedMeProducer javax.ws.rs.NotFoundException"));
    }
}