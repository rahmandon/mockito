package org.mockito.plugin.javassist;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;
import org.mockito.internal.Incubating;
import org.mockito.plugins.MockMaker;
import org.mockito.plugins.MockSettingsInfo;
import org.mockito.plugins.MockitoInvocationHandler;

/**
 *
 */
@Incubating
public class JavassistMockMaker implements MockMaker {

    public <T> T createMock(
            Class<T> typeToMock,
            Class<?>[] extraInterfaces,
            MockitoInvocationHandler mockitoInvocationHandler,
            MockSettingsInfo settings ) {

        return JavassistClassImposterizer.INSTANCE.imposterise(
                new JavassistMethodInterceptorFilter(mockitoInvocationHandler, settings),
                typeToMock,
                extraInterfaces
        );
    }

    public MockitoInvocationHandler getHandler(Object mock) {
        if (!(mock instanceof ProxyObject)) {
            return null;
        }
        ProxyObject proxyObject = (ProxyObject) mock;
        MethodHandler javassistHandler = proxyObject.getHandler();
        if (javassistHandler instanceof JavassistMethodInterceptorFilter) {
            JavassistMethodInterceptorFilter handler = (JavassistMethodInterceptorFilter) javassistHandler;
            return handler.getMockitoHandler();
        }
        return null;
    }

    public void resetMock(Object mock, MockitoInvocationHandler newMockitoInvocationHandler, MockSettingsInfo settings) {
        ((ProxyObject) mock).setHandler(
                new JavassistMethodInterceptorFilter(
                        newMockitoInvocationHandler,
                        settings
                )
        );
    }
}
