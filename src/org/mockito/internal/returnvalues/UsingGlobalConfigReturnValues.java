/**
 * 
 */
package org.mockito.internal.returnvalues;

import org.mockito.configuration.ReturnValues;
import org.mockito.internal.configuration.Configuration;
import org.mockito.invocation.InvocationOnMock;

public final class UsingGlobalConfigReturnValues implements ReturnValues {
    public Object valueFor(InvocationOnMock invocation) {
        return Configuration.instance().getReturnValues().valueFor(invocation);
    }
}