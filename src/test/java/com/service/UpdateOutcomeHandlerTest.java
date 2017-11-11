package com.service;

import com.db.CouchbaseRepositoryServiceImpl;
import com.db.EventRetrieverService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Market;
import com.model.Outcome;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class UpdateOutcomeHandlerTest {

    public static final String MARKET_ID = "marketId1";
    public static final String EVENT_ID = "eventId";
    public static final String TEST = "TEST";
    public static final String UPDATED_VALUE = "UPDATED_VALUE";
    public static final String OUTCOMEID = "outcomeid1";
    public static final String OUTCOMEID_1 = "outcomeid2";

    UpdateOutcomeHandler updateOutcomeHandler;

    @Mock
    EventRetrieverService eventRetrieverService;

    @Mock
    CouchbaseRepositoryServiceImpl repositoryService;

    Market market;
    Outcome outcome;
    Outcome outcome2;

    BaseMarket baseMarket2;

    BaseEvent baseEvent;
    Optional<BaseEvent> baseEventOptional;

    List<Outcome> outcomes = new ArrayList<>();
    List<BaseMarket> markets = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        outcome = Outcome.OutcomeBuilder.anOutcome().withOutcomeId(OUTCOMEID_1)
                .withMarketId(MARKET_ID).withName(UPDATED_VALUE).build();

        market = Market.MarketBuilder.aMarket().withEventId(EVENT_ID)
                .withMarketId(MARKET_ID).build();

        outcome2 = Outcome.OutcomeBuilder.anOutcome().withOutcomeId(OUTCOMEID_1)
                .withMarketId(MARKET_ID).withName(TEST).build();
        outcomes.add(outcome2);
        baseMarket2 = BaseMarket.BaseMarketBuilder.aBaseMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID)
                .withName(UPDATED_VALUE)
                .withOutcomes(outcomes).build();

        markets.add(baseMarket2);
        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent().withEventId(EVENT_ID)
                .withMarkets(markets).build();
        baseEventOptional = Optional.of(baseEvent);

        updateOutcomeHandler = new UpdateOutcomeHandler(eventRetrieverService, repositoryService);
    }

    @Test
    public void handle_updateOutcomeSuccessfully() throws Exception {

        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptional);

        updateOutcomeHandler.handle(outcome, EVENT_ID);

        assertThat(baseEventOptional.get().getMarkets().size(), is(1));

        assertThat(baseEventOptional.get().getMarkets().get(0).getOutcomes().size(), is(1));
        assertThat(baseEventOptional.get().getMarkets().get(0).getOutcomes().get(0).getName(), is(UPDATED_VALUE));

        verify(repositoryService, times(1)).updateDocument(any());

    }

}