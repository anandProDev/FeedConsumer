package com.service;

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