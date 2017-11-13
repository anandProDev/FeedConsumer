package com.factory;

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
public class MarketHandlerFactoryTest {

    MarketHandlerFactory marketHandlerFactory;

    @Mock
    CreateMarketHandler createMarketHandler;
    @Mock
    UpdateMarketHandler updateMarketHandler;

    @Before
    public void setUp() throws Exception {
        marketHandlerFactory = new MarketHandlerFactory(createMarketHandler, updateMarketHandler);
    }

    @Test
    public void getHandler() throws Exception {

        MarketHander create = marketHandlerFactory.getHandler("create").get();
        assertTrue(create instanceof CreateMarketHandler);

        MarketHander update = marketHandlerFactory.getHandler("update").get();
        assertTrue(update instanceof UpdateMarketHandler);

        assertFalse(marketHandlerFactory.getHandler("unknown").isPresent());
    }

}