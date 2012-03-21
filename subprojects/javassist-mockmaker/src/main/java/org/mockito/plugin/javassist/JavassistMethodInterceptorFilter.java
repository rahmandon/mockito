package org.mockito.plugin.javassist;

import javassist.util.proxy.MethodHandler;
import org.mockito.internal.creation.DelegatingMethod;
import org.mockito.internal.invocation.Invocation;
import org.mockito.internal.invocation.MockitoMethod;
import org.mockito.internal.invocation.SerializableMethod;
import org.mockito.internal.invocation.realmethod.RealMethod;
import org.mockito.internal.progress.SequenceNumber;
import org.mockito.internal.util.ObjectMethodsGuru;
import org.mockito.plugins.MockSettingsInfo;
import org.mockito.plugins.MockitoInvocationHandler;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 */
public class JavassistMethodInterceptorFilter implements MethodHandler, Serializable {

    private static final long serialVersionUID = 5527623636368558225L;

    ObjectMethodsGuru objectMethodsGuru = new ObjectMethodsGuru();
    private final MockitoInvocationHandler handler;
    private final MockSettingsInfo mockSettings;


    public JavassistMethodInterceptorFilter(MockitoInvocationHandler handler, MockSettingsInfo mockSettings) {
        this.handler = handler;
        this.mockSettings = mockSettings;
    }

    public Object invoke(Object proxy, Method proxyMethod, Method typeMethod, Object[] args) throws Throwable {
        if (objectMethodsGuru.isEqualsMethod(proxyMethod)) {
            return proxy == args[0];
        } else if (objectMethodsGuru.isHashCodeMethod(proxyMethod)) {
            return hashCodeForMock(proxy);
        }

        MockitoMethod mockitoMethod = createMockitoMethod(proxyMethod);
        Invocation invocation = new Invocation(proxy, mockitoMethod, args, SequenceNumber.next(), realMethod(typeMethod));
        return handler.handle(invocation);
    }

    private RealMethod realMethod(final Method method) {
        if(method == null) {
            return new JavassistAbstractRealMethod();
        }
        return new JavassistRealMethod(createMockitoMethod(method));
    }

    private int hashCodeForMock(Object mock) {
        return System.identityHashCode(mock);
    }

    public MockitoMethod createMockitoMethod(Method method) {
        if (mockSettings.isSerializable()) {
            return new SerializableMethod(method);
        } else {
            return new DelegatingMethod(method);
        }
    }

    public MockitoInvocationHandler getMockitoHandler() {
        return handler;
    }

}
