package org.mockito.plugin.javassist;

import org.mockito.internal.exceptions.base.ConditionalStackTraceFilter;
import org.mockito.internal.invocation.MockitoMethod;
import org.mockito.internal.invocation.realmethod.RealMethod;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
*
*/
class JavassistRealMethod implements RealMethod, Serializable {
    private static final long serialVersionUID = 3608511690179016513L;

    private final MockitoMethod mockitoMethod;

    public JavassistRealMethod(MockitoMethod mockitoMethod) {
        this.mockitoMethod = mockitoMethod;
    }

    public Object invoke(Object target, Object[] arguments) throws Throwable {
        try {
            return mockitoMethod.getJavaMethod().invoke(target, arguments);
        } catch (InvocationTargetException ite) {
            Throwable t = ite.getTargetException();
            new ConditionalStackTraceFilter().filter(t);
            throw t;
        }
    }
}
