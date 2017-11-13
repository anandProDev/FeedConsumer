package com.model;

import com.factory.UnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class OutcomeTest {

    public static final String OUTCOME_ID = "1";
    public static final String MARKET_ID = "2";
    public static final String PRICE = "test price";
    public static final String NAME = "test name";

    @Mock
    Header header;
    Outcome outcome;
    @Before
    public void setUp(){

        outcome = Outcome.OutcomeBuilder.anOutcome().withHeader(header)
                .withMarketId(MARKET_ID)
                .withOutcomeId(OUTCOME_ID)
                .withPrice(PRICE)
                .withName(NAME)
                .withDisplayed(Boolean.TRUE)
                .withSuspended(Boolean.FALSE)
                .build();
    }

    @Test
    public void Outcome_is_build_with_expected_values() throws Exception {

        assertThat(header, is(outcome.getHeader()));
        assertThat(MARKET_ID, is(outcome.getMarketId()));
        assertThat(OUTCOME_ID, is(outcome.getOutcomeId()));
        assertThat(PRICE, is(outcome.getPrice()));
        assertThat(NAME, is(outcome.getName()));
        assertThat(Boolean.TRUE, is(outcome.isDisplayed()));
        assertThat(Boolean.FALSE, is(outcome.isSuspended()));
    }

    @Test
    public void equals() throws Exception {

        Outcome outcome1 = Outcome.OutcomeBuilder.anOutcome().withMarketId(MARKET_ID).withOutcomeId(OUTCOME_ID).build();
        Outcome outcome2 = Outcome.OutcomeBuilder.anOutcome().withMarketId(MARKET_ID).withOutcomeId(OUTCOME_ID).build();
        assertTrue(outcome1.equals(outcome2));
        assertFalse(outcome1.equals(null));

        outcome2 = Outcome.OutcomeBuilder.anOutcome().withMarketId(MARKET_ID).withOutcomeId("3").build();
        assertFalse(outcome1.equals(outcome2));
    }

    @Test
    public void toString_ReturnsExpectedValue()  {
        Assert.assertThat(outcome.toString(), is("Outcome{header=header, marketId='2', outcomeId='1', name='test name', price='test price', displayed=true, suspended=false}"));
    }
}