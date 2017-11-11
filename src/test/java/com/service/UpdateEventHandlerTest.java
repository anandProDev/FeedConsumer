package com.service;

import com.db.EventRetrieverService;
import com.db.RepositoryService;
import com.domain.BaseEvent;
import com.domain.BaseMarket;
import com.model.Event;
import com.model.Outcome;
import com.transformer.BaseEventTransformer;
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
public class UpdateEventHandlerTest {

    public static final String EVENT_ID = "eventId";
    public static final String MARKET_ID = "marketId";

    EventHandler eventHandler;

    @Mock
    BaseEventTransformer baseEventTransformer;

    @Mock
    RepositoryService repositoryService;

    @Mock
    EventRetrieverService eventRetrieverService;

    Event event = Event.EventBuilder.anEvent().withEventId(EVENT_ID).build();

    BaseEvent baseEvent = BaseEvent.BaseEventBuilder.aBaseEvent().withEventId(EVENT_ID).build();

    Optional<BaseEvent> baseEventOptional;
    private java.util.Optional<com.domain.BaseEvent> baseEventOptionalCB;
    private BaseEvent baseEventCb;

    BaseMarket baseMarket;
    private List<BaseMarket> markets = new ArrayList<>();
    Outcome outcome;
    private List<Outcome> outcomes = new ArrayList<>();


    @Before
    public void setUp(){

        baseEventOptional = Optional.of(baseEvent);

        outcome = Outcome.OutcomeBuilder.anOutcome().withMarketId(MARKET_ID).build();
        outcomes.add(outcome);
        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket().withEventId(EVENT_ID).withMarketId(MARKET_ID)
                .withOutcomes(outcomes).build();
        markets.add(baseMarket);
        baseEventCb = BaseEvent.BaseEventBuilder.aBaseEvent().withEventId(EVENT_ID).withMarkets(markets).build();
        baseEventOptionalCB = Optional.of(baseEventCb);

        eventHandler = new UpdateEventHandler(baseEventTransformer, repositoryService, eventRetrieverService);
    }

    @Test
    public void handleEvent_successful() throws Exception {

        when(baseEventTransformer.transform(event)).thenReturn(baseEventOptional);

        when(eventRetrieverService.getBaseEvent(EVENT_ID)).thenReturn(baseEventOptionalCB);

        eventHandler.handle(event);

        BaseEvent baseEvent = baseEventOptional.get();

        assertThat(baseEvent.getEventId(), is(EVENT_ID));
        assertThat(baseEvent.getMarkets().size(), is(1));

        BaseMarket market = baseEvent.getMarkets().get(0);
        assertThat(market.getEventId(), is(EVENT_ID));
        assertThat(market.getMarketId(), is(MARKET_ID));
        assertThat(market.getOutcomes().size(), is(1));

        Outcome outcome = market.getOutcomes().get(0);
        assertThat(outcome.getMarketId(), is(MARKET_ID));

        verify(repositoryService, times(1)).updateDocument(any());
    }

    @Test
    public void eventIsNotUpdatedWhenTransformationErrorOccours() throws Exception {

        baseEventOptional = Optional.empty();
        when(baseEventTransformer.transform(event)).thenReturn(baseEventOptional);

        eventHandler.handle(event);

        verify(repositoryService, times(0)).updateDocument(any());
    }
}