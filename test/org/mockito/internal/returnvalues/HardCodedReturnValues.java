package org.mockito.internal.returnvalues;

import org.mockito.configuration.ReturnValues;
import org.mockito.invocation.InvocationOnMock;

public class HardCodedReturnValues implements ReturnValues {
    private final Object customResult;

    public HardCodedReturnValues(Object customResult) {
        this.customResult = customResult;
    }

    public Object valueFor(InvocationOnMock invocation) {
        return customResult;
    }
}