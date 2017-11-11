package com.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@Category(UnitTest.class)

public class OperationTest {

    @Test
    public void getName() throws Exception {

        assertThat("create", is(Operation.CREATE.getName()));
        assertThat("update", is(Operation.UPDATE.getName()));
    }

}