package org.mockito.plugin.javassist;

import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.invocation.realmethod.RealMethod;

import java.io.Serializable;

/**
 *
 */
public class JavassistAbstractRealMethod implements RealMethod, Serializable {
    private static final long serialVersionUID = -5784315762855902846L;

    public Object invoke(Object target, Object[] arguments) throws Throwable {
        throw new MockitoException("Cannot invoke an abstract method, should neither be thrown, as Mockito already do this check");
    }
}
