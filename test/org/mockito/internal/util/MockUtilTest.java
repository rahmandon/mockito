/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.util;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.configuration.ReturnValues;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.exceptions.misusing.NotAMockException;
import org.mockito.internal.progress.MockingProgressImpl;
import org.mockito.invocation.InvocationOnMock;
import org.mockitoutil.TestBase;

public class MockUtilTest extends TestBase {

    @Test 
    public void shouldGetHandler() {
        List<?> mock = Mockito.mock(List.class);
        assertNotNull(MockUtil.getMockHandler(mock));
    }

    @Test 
    public void shouldScreamWhenEnhancedButNotAMockPassed() {
        Object o = Enhancer.create(ArrayList.class, NoOp.INSTANCE);
        try {
            MockUtil.getMockHandler(o);
            fail();
        } catch (NotAMockException e) {}
    }

    @Test (expected=NotAMockException.class)
    public void shouldScreamWhenNotAMockPassed() {
        MockUtil.getMockHandler("");
    }
    
    @Test (expected=MockitoException.class)
    public void shouldScreamWhenNullPassed() {
        MockUtil.getMockHandler(null);
    }
    
    @Test
    public void shouldValidateMock() {
        assertFalse(MockUtil.isMock("i mock a mock"));
        assertTrue(MockUtil.isMock(Mockito.mock(List.class)));
    }
    
    public static class CustomReturnValues implements ReturnValues {
        private final String customResult = "custom result";
        public Object valueFor(InvocationOnMock invocation) {
            return customResult;
        }
    }
    
    @Test
    public void shouldUseCustomReturnValues() throws Exception {
        CustomReturnValues returnValues = new CustomReturnValues();
        List<?> mock = MockUtil.createMock(List.class, new MockingProgressImpl(), null, null, returnValues);
        assertThat(mock.toString(), is("custom result"));
    }
}
