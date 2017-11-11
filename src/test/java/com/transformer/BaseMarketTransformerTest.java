package com.transformer;

import com.domain.BaseMarket;
import com.model.Header;
import com.model.Market;
import com.service.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class BaseMarketTransformerTest {

    public static final String EVENT_ID = "1";
    public static final String MARKET_ID = "2";
    public static final String NAME = "test name";

    @Mock
    Header header;

    Market market;

    BaseMarketTransformer baseMarketTransformer;
    @Before
    public void setUp(){

        market = Market.MarketBuilder.aMarket().withHeader(header)
                .withEventId(EVENT_ID)
                .withMarketId(MARKET_ID)
                .withName(NAME)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.FALSE)
                .build();

        baseMarketTransformer = new BaseMarketTransformerImpl();

    }

    @Test
    public void Market_is_build_with_expected_values() throws Exception {

        BaseMarket market = baseMarketTransformer.transform(this.market).get();

        assertThat(header, is(market.getHeader()));
        assertThat(EVENT_ID, is(market.getEventId()));
        assertThat(MARKET_ID, is(market.getMarketId()));
        assertThat(NAME, is(market.getName()));
        assertThat(Boolean.TRUE, is(market.isDisplayed()));
        assertThat(Boolean.FALSE, is(market.isSuspended()));
        assertTrue(market.getOutcomes().isEmpty());
    }

    @Test
    public void error_transforming_BaseEvent() throws Exception {

        Optional<BaseMarket> baseMarket = baseMarketTransformer.transform(null);
        assertFalse(baseMarket.isPresent());
    }


}