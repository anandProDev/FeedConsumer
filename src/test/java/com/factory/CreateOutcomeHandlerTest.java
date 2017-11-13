package com.factory;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class CreateOutcomeHandlerTest {

    public static final String MARKET_ID = "marketId1";
    public static final String EVENT_ID = "eventId";
    CreateOutcomeHandler createOutcomeHandler;

    @Mock
    RepositoryService repositoryService;

    @Mock
    EventRetrieverService eventRetrieverService;

    Outcome outcome;

    Optional<BaseEvent> baseEventOptional;

    BaseEvent baseEvent;

    BaseMarket baseMarket;
    Optional<BaseMarket> baseMarketOptional;

    private List<BaseMarket> markets = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        createOutcomeHandler = new CreateOutcomeHandler(eventRetrieverService, repositoryService);

        outcome = Outcome.OutcomeBuilder.anOutcome()
                .withMarketId(MARKET_ID).build();

        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket()
                .withMarketId(MARKET_ID).withEventId(EVENT_ID).build();

        baseMarketOptional = Optional.of(baseMarket);


        markets.add(BaseMarket.BaseMarketBuilder.aBaseMarket().withMarketId(MARKET_ID).build());
        baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent()
                .withEventId(EVENT_ID)
                .withMarkets(markets)
                .build();
        baseEventOptional = Optional.of(baseEvent);
    }

    @Test
    public void handle_successful() throws Exception {

        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptional);

        createOutcomeHandler.handle(outcome, EVENT_ID);

        List<Outcome> outcomes = baseEvent.getMarkets().get(0).getOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getMarketId(), is(MARKET_ID));

        verify(repositoryService, times(1)).insertDocument(any());
    }


    @Test
    public void no_matching_event_found() throws Exception {

        outcome = Outcome.OutcomeBuilder.anOutcome()
                .withMarketId("1").build();

        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptional);

        createOutcomeHandler.handle(outcome, EVENT_ID);

        assertTrue(baseEvent.getMarkets().get(0).getOutcomes().isEmpty());

        verify(repositoryService, times(1)).insertDocument(any());
    }

}