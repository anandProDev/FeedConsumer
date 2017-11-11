package com.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)
public class OutcomeHandlerFactoryTest {

    OutcomeHandlerFactory outcomeHandlerFactor;

    @Mock
    CreateOutcomeHandler createOutcomeHandler;

    @Mock
    UpdateOutcomeHandler updateOutcomeHandler;


    @Before
    public void setUp() throws Exception {
        outcomeHandlerFactor = new OutcomeHandlerFactory(createOutcomeHandler, updateOutcomeHandler);
    }

    @Test
    public void getHandler() throws Exception {

        OutcomeHandler create = outcomeHandlerFactor.getHandler("create").get();
        assertTrue(create instanceof CreateOutcomeHandler);

        OutcomeHandler update = outcomeHandlerFactor.getHandler("update").get();
        assertTrue(update instanceof UpdateOutcomeHandler);

        assertFalse(outcomeHandlerFactor.getHandler("unknown").isPresent());

    }

}