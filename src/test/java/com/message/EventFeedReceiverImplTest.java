package com.message;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Event;
import com.model.Market;
import com.model.Outcome;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class EventFeedReceiverImplTest {

    public static final String EVENT = "event";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String MESSAGE = "test";
    private static final String MARKET = "market";
    private static final String OUTCOME = "outcome";
    EventFeedReceiverImpl eventFeedReceiver;

    @Mock
    EventHandlerFactory eventHandlerFactory;
    @Mock
    MarketHandlerFactory marketHandlerFactory;
    @Mock
    OutcomeHandlerFactory outcomeHandlerFactory;

    Optional<EventHandler> eventHandlerOptional;
    Optional<MarketHander> marketHanderOptional;
    Optional<OutcomeHandler> outcomeHandlerOptional;

    @Mock
    CreateEventHandler createEventHandler;
    @Mock
    CreateMarketHandler createMarketHandler;
    @Mock
    CreateOutcomeHandler createOutcomeHandler;

    @Mock
    UpdateEventHandler updateEventHandler;
    @Mock
    UpdateMarketHandler updateMarketHandler;
    @Mock
    UpdateOutcomeHandler updateOutcomeHandler;

    @Mock
    Event event;

    @Mock
    Market market;

    @Mock
    Outcome outcome;

    @Mock
    Channel channel;

    @Mock
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        eventFeedReceiver = new EventFeedReceiverImpl(eventHandlerFactory, marketHandlerFactory, outcomeHandlerFactory);
        eventFeedReceiver.mapper = objectMapper;
    }

    @Test
    public void getDefaultConsumer() throws Exception {

        DefaultConsumer defaultConsumer = eventFeedReceiver.getDefaultConsumer(channel);

        assertNotNull(defaultConsumer);
    }

    @Test
    public void handleEventsSuccessfully_forCreateHandler() throws Exception {
        eventHandlerOptional = Optional.of(createEventHandler);
        marketHanderOptional = Optional.of(createMarketHandler);
        outcomeHandlerOptional = Optional.of(createOutcomeHandler);

        when(objectMapper.readValue(MESSAGE, Event.class)).thenReturn(event);
        when(eventHandlerFactory.getHandler(CREATE)).thenReturn(eventHandlerOptional);
        eventFeedReceiver.handleMessages(MESSAGE, EVENT, CREATE);
        verify(createEventHandler, times(1)).handle(event);

        when(objectMapper.readValue(MESSAGE, Market.class)).thenReturn(market);
        when(marketHandlerFactory.getHandler(CREATE)).thenReturn(marketHanderOptional);
        eventFeedReceiver.handleMessages(MESSAGE, MARKET, CREATE);
        verify(createMarketHandler, times(1)).handle(market);

        when(objectMapper.readValue(MESSAGE, Outcome.class)).thenReturn(outcome);
        when(outcomeHandlerFactory.getHandler(CREATE)).thenReturn(outcomeHandlerOptional);
        eventFeedReceiver.handleMessages(MESSAGE, OUTCOME, CREATE);
        verify(createOutcomeHandler, times(1)).handle(any(Outcome.class), anyString());
    }

    @Test
    public void handleEventsSuccessfully_forUpdateHandler() throws Exception {
        eventHandlerOptional = Optional.of(updateEventHandler);
        marketHanderOptional = Optional.of(updateMarketHandler);
        outcomeHandlerOptional = Optional.of(updateOutcomeHandler);

        when(objectMapper.readValue(MESSAGE, Event.class)).thenReturn(event);
        when(eventHandlerFactory.getHandler(UPDATE)).thenReturn(eventHandlerOptional);
        eventFeedReceiver.handleMessages(MESSAGE, EVENT, UPDATE);
        verify(updateEventHandler, times(1)).handle(event);

        when(objectMapper.readValue(MESSAGE, Market.class)).thenReturn(market);
        when(marketHandlerFactory.getHandler(UPDATE)).thenReturn(marketHanderOptional);
        eventFeedReceiver.handleMessages(MESSAGE, MARKET, UPDATE);
        verify(updateMarketHandler, times(1)).handle(market);

        when(objectMapper.readValue(MESSAGE, Outcome.class)).thenReturn(outcome);
        when(outcomeHandlerFactory.getHandler(UPDATE)).thenReturn(outcomeHandlerOptional);
        eventFeedReceiver.handleMessages(MESSAGE, OUTCOME, UPDATE);
        verify(updateOutcomeHandler, times(1)).handle(any(Outcome.class), anyString());
    }

    @Test(expected = IOException.class)
    public void handleEvents_throwsException_whenMappingEvent() throws Exception {

        when(objectMapper.readValue(MESSAGE, Event.class)).thenThrow(IOException.class);
        eventFeedReceiver.handleMessages(MESSAGE, EVENT, CREATE);
        verify(createEventHandler, times(0)).handle(event);
    }
}