package com.service;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Market;
import com.transformer.BaseMarketTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class CreateMarketHandlerTest {

    public static final String MARKET_ID = "marketId1";
    public static final String EVENT_ID = "eventId";
    CreateMarketHandler createMarketHandler;

    @Mock
    BaseMarketTransformer baseMarketTransformer;
    @Mock
    EventRetrieverService eventRetrieverService;
    @Mock
    RepositoryService repositoryService;

    @Mock
    Market market;

    Optional<BaseEvent> baseEventOptional;

    BaseEvent baseEvent;

    BaseMarket baseMarket;
    Optional<BaseMarket> baseMarketOptional;

    private List<BaseMarket> markets = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        createMarketHandler = new CreateMarketHandler(baseMarketTransformer, eventRetrieverService, repositoryService);

        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket()
                .withMarketId(MARKET_ID).withEventId(EVENT_ID).build();

        baseMarketOptional = Optional.of(baseMarket);

        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent()
                .withEventId(EVENT_ID)
                .withMarkets(markets)
                .build();
        baseEventOptional = Optional.of(baseEvent);
    }

    @Test
    public void handle_successful() {
        when(baseMarketTransformer.transform(market)).thenReturn(baseMarket);
        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptional);

        createMarketHandler.handle(market);

        List<BaseMarket> markets = baseEventOptional.get().getMarkets();

        assertThat(markets.size(), is(1));
        BaseMarket baseMarket = markets.get(0);
        assertThat(baseMarket.getMarketId(), is(MARKET_ID));
        assertThat(baseMarket.getEventId(), is(EVENT_ID));

        verify(repositoryService, times(1)).insertDocument(any());
    }

}