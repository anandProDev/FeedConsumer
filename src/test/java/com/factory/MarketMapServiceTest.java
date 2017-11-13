package com.factory;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.db.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class MarketMapServiceTest {


    public static final String MARKET_EVENT_MAP = "marketEventMap";
    public static final String EVENT_ID = "eventId";
    public static final String MARKET_ID = "marketId";

    MarketMapService marketMapService;

    @Mock
    RepositoryService repositoryService;

    Map map = new HashMap<String, String>();

    JsonObject jsonObject = JsonObject.fromJson("{\"marketId\":\"eventId\"}");
    private final JsonDocument marketJsonDoc = JsonDocument.create(MARKET_EVENT_MAP, jsonObject);


    @Before
    public void setUp() throws Exception {

        JsonDocument.create("\"{\\\"marketId\\\":\\\"eventId\\\"}\"");
        marketMapService = new MarketMapServiceImpl(repositoryService);

        map.put(MARKET_ID, EVENT_ID);
    }

    @Test
    public void marketMapLoad_successful() throws Exception {

        when(repositoryService.getDocument(MARKET_EVENT_MAP)).thenReturn(marketJsonDoc);

        Map<String, String> load = marketMapService.load().get();

        assertNotNull(load);
        assertThat(load.get(MARKET_ID) , is(EVENT_ID));
    }

    @Test
    public void marketMapLoad_isNotLoaded() throws Exception {

        when(repositoryService.getDocument(MARKET_EVENT_MAP)).thenThrow(IOException.class);
        assertFalse(marketMapService.load().isPresent());
    }

    @Test
    public void update_marketMapSuccessful() throws Exception {

        when(repositoryService.getDocument(MARKET_EVENT_MAP)).thenReturn(marketJsonDoc);

        marketMapService.save(map);

        verify(repositoryService, times(1)).updateDocument(marketJsonDoc);
    }

    @Test
    public void insert_marketMapSuccessful() throws Exception {

        when(repositoryService.getDocument(MARKET_EVENT_MAP)).thenReturn(null);

        marketMapService.save(map);

        verify(repositoryService, times(1)).insertDocument(marketJsonDoc);
    }

}