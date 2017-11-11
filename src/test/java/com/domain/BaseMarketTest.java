package com.domain;

import com.model.Header;
import com.model.Outcome;
import com.service.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class BaseMarketTest {

    public static final String EVENT_ID = "1";
    public static final String MARKET_ID = "2";
    public static final String NAME = "test name";
    BaseMarket baseMarket;

    @Mock
    Header header;

    @Mock
    private List<Outcome> outcomes;

    @Before
    public void setUp(){

        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket()
                .withHeader(header)
                .withEventId(EVENT_ID)
                .withMarketId(MARKET_ID)
                .withName(NAME)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.FALSE)
                .withOutcomes(outcomes)
                .build();
    }

    @Test
    public void Market_is_build_with_expected_values() throws Exception {

        assertThat(header, is(baseMarket.getHeader()));
        assertThat(EVENT_ID, is(baseMarket.getEventId()));
        assertThat(MARKET_ID, is(baseMarket.getMarketId()));
        assertThat(NAME, is(baseMarket.getName()));
        assertThat(Boolean.TRUE, is(baseMarket.isDisplayed()));
        assertThat(Boolean.FALSE, is(baseMarket.isSuspended()));
        assertThat(outcomes, is(baseMarket.getOutcomes()));
    }

    @Test
    public void toString_ReturnsExpectedValue()  {
        assertThat(baseMarket.toString(), is("BaseMarket{header=header, eventId='1', marketId='2', name='test name', displayed=true, suspended=false, outcomes=outcomes}"));
    }

    @Test
    public void hashcode() throws Exception {

        assertThat(baseMarket.hashCode(), is(1569));

        baseMarket = BaseMarket.BaseMarketBuilder.aBaseMarket()
                .withMarketId("myMarketId")
                .withEventId("myEventId")
                .build();

        assertThat(baseMarket.hashCode(), is(1446770522));

    }
}