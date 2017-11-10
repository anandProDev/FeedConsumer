package com.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeedTypeTest {

    @Test
    public void getName() throws Exception {

        String name = FeedType.EVENT.getName();
        assertThat(name, is("event"));

        name = FeedType.MARKET.getName();
        assertThat(name, is("market"));

        name = FeedType.OUTCOME.getName();
        assertThat(name, is("outcome"));
    }
}