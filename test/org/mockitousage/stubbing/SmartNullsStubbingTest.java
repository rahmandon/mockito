/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockitousage.stubbing;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.SmartNullPointerException;
import org.mockitousage.IMethods;
import org.mockitoutil.TestBase;

@SuppressWarnings("unchecked")
public class SmartNullsStubbingTest extends TestBase {

    private IMethods mock;

    @Before
    public void setup() {
        mock = mock(IMethods.class, Mockito.SMART_NULLS);
    }

    @Test
    public void shouldThrowSmartNPE() throws Exception {
        List list = mock.listReturningMethod();
        try {
            list.clear();
            fail();
        } catch (SmartNullPointerException e) {}
    }
}