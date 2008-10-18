package org.mockitousage.examples.configure.eachmock;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.internal.returnvalues.HardCodedReturnValues;


public class ConfiguringReturnValuesForEachMockTest {
    public static interface DoesSomething {
        public Object doSomething();
    }
    
    @Test
    public void createsMockWithCustomReturnValues() throws Exception {
        final Object customResult = new Object();
        DoesSomething mock = mock(DoesSomething.class, new HardCodedReturnValues(customResult));
        
        Object result = mock.doSomething();
        assertThat(result, is(customResult));
    }
}
