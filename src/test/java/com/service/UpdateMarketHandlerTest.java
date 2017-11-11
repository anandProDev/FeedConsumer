package com.service;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Market;
import com.model.Outcome;
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
public class UpdateMarketHandlerTest {

    public static final String MARKET_ID = "marketId1";
    public static final String MARKET_ID_2 = "marketId2";
    public static final String EVENT_ID = "eventId";
    public static final String TEST = "TEST";
    public static final String UPDATED_VALUE = "UPDATED_VALUE";
    public static final String OUTCOMEID_1 = "outcomeid1";

    UpdateMarketHandler updateMarketHandler;

    @Mock
    BaseMarketTransformer baseMarketTransformer;
    @Mock
    EventRetrieverService eventRetrieverService;
    @Mock
    RepositoryService repositoryService;

    Market market;

    Outcome outcome;

    BaseMarket baseMarket;
    Optional<BaseMarket> baseMarketOptional;

    BaseMarket baseMarket1;
    BaseMarket baseMarket2;

    BaseEvent baseEvent;
    Optional<BaseEvent> baseEventOptional;

    List<Outcome> outcomes = new ArrayList<>();
    List<BaseMarket> markets = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        market = Market.MarketBuilder.aMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID).build();
        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID)
                .withName(TEST)
                .build();

        baseMarketOptional = Optional.of(baseMarket);

        baseMarket1 = BaseMarket.BaseMarketBuilder.aBaseMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID_2)
                .withOutcomes(outcomes).build();

        outcome = Outcome.OutcomeBuilder.anOutcome().withOutcomeId(OUTCOMEID_1).build();
        outcomes.add(outcome);
        baseMarket2 = BaseMarket.BaseMarketBuilder.aBaseMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID)
                        .withName(UPDATED_VALUE)
                        .withOutcomes(outcomes).build();

        markets.add(baseMarket1);
        markets.add(baseMarket2);
        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent().withEventId(EVENT_ID)
                .withMarkets(markets).build();
        baseEventOptional = Optional.of(baseEvent);

        updateMarketHandler = new UpdateMarketHandler(baseMarketTransformer, eventRetrieverService,repositoryService);
    }

    @Test
    public void handleUpdate_successful() throws Exception {

        when(baseMarketTransformer.transform(market)).thenReturn(baseMarketOptional);
        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptional);

        updateMarketHandler.handle(market);

        assertThat(baseEventOptional.get().getMarkets().size(), is(2));
        assertThat(baseEventOptional.get().getMarkets().get(1).getName(), is(TEST));

        verify(repositoryService, times(1)).updateDocument(any());
    }

    @Test
    public void updateMarket_withOutcome() throws Exception {

        updateMarketHandler.updateMarket(baseMarket, markets);

        assertThat(markets.size(), is(2));
        assertThat(markets.get(1).getOutcomes().size(), is(1));
        assertThat(markets.get(1).getOutcomes().get(0).getOutcomeId(), is(OUTCOMEID_1));
    }

}