package com.model;

import com.service.UnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class MarketTest {

    public static final String EVENT_ID = "1";
    public static final String MARKET_ID = "2";
    public static final String NAME = "test name";

    @Mock
    Header header;

    Market market;

    @Before
    public void setUp(){

        market = Market.MarketBuilder.aMarket().withHeader(header)
                .withEventId(EVENT_ID)
                .withMarketId(MARKET_ID)
                .withName(NAME)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.FALSE)
                .build();
    }

    @Test
    public void Market_is_build_with_expected_values() throws Exception {

        assertThat(header, is(market.getHeader()));
        assertThat(EVENT_ID, is(market.getEventId()));
        assertThat(MARKET_ID, is(market.getMarketId()));
        assertThat(NAME, is(market.getName()));
        assertThat(Boolean.TRUE, is(market.isDisplayed()));
        assertThat(Boolean.FALSE, is(market.isSuspended()));
    }

    @Test
    public void toString_ReturnsExpectedValue()  {
        Assert.assertThat(market.toString(), is("Market{header=header, eventId='1', marketId='2', name='test name', displayed=true, suspended=false}"));
    }
}