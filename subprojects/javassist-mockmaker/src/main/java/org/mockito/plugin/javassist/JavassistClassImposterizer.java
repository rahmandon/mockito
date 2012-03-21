package org.mockito.plugin.javassist;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyObject;
import org.mockito.internal.Incubating;
import org.mockito.internal.configuration.GlobalConfiguration;
import org.mockito.internal.creation.jmock.SearchingClassLoader;
import org.mockito.plugin.javassist.ProxyFactory.ClassLoaderProvider;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@Incubating
public class JavassistClassImposterizer {

    public static final JavassistClassImposterizer INSTANCE = new JavassistClassImposterizer();

    private ObjenesisStd objenesis = new ObjenesisStd(new GlobalConfiguration().enableClassCache());


    public <T> T imposterise(
            MethodHandler methodHandler,
            Class<T> typeToMock,
            Class<?>[] extraInterfaces) {

        setConstructorsAccessible(typeToMock, true);

        Class<?> proxyClass = createProxyClass(typeToMock, extraInterfaces);

        Object proxy = createProxy(proxyClass, methodHandler);

        return typeToMock.cast(proxy);
    }


    private Object createProxy(Class<?> proxyClass, final MethodHandler handler) {
        ProxyObject proxy = (ProxyObject) objenesis.newInstance(proxyClass);
        proxy.setHandler(handler);
        return proxy;
    }


    private Class<?> createProxyClass(Class<?> typeToMock, Class<?>... extraInterfaces) {
//            ClassPool pool = ClassPool.getDefault();
//
//            CtClass cc = pool.get(typeToMock.getCanonicalName() + "$WithMockitoEnhancer");
//            cc.setSuperclass(pool.get(typeToMock.getCanonicalName()));
//
//            ClassFile classFile = cc.getClassFile();

//            Class mockClass = cc.toClass(
//                    SearchingClassLoader.combineLoadersOf(typeToMock),
//                    typeToMock.getProtectionDomain()
//            );
            if (typeToMock == Object.class) {
                typeToMock = ClassWithSuperclassToWorkAroundJavassistBug.class;
            }

//            ProxyFactory proxyFactory = new ProxyFactory() {
//                @Override protected ClassLoader getClassLoader() {
//                    return SearchingClassLoader.combineLoadersOf(typeToMock);
//                }
//            };
            ProxyFactory proxyFactory = new ProxyFactory();
            ProxyFactory.classLoaderProvider = new ClassLoaderProvider() {
                public ClassLoader get(ProxyFactory pf) {
                    return SearchingClassLoader.combineLoadersOf(pf.getSuperclass());
                }
            };

            if (typeToMock.isInterface()) {
                proxyFactory.setInterfaces(prepend(typeToMock, extraInterfaces));
            } else {
                proxyFactory.setSuperclass(typeToMock);
                proxyFactory.setInterfaces(extraInterfaces);
            }

            proxyFactory.setFilter(new MethodFilter() {
                public boolean isHandled(Method method) {
                    return !method.isBridge();
                }
            });

            proxyFactory.setUseWriteReplace(false);


            Class mockClass = proxyFactory.createClass();

            return mockClass;

//        } catch (NotFoundException e) {
//            throw new MockitoException(join("javassist failed"), e);
//        } catch (CannotCompileException e) {
//            throw new MockitoException(join("javassist failed"), e);
//        }
//
//
//        return null;
    }

    private void setConstructorsAccessible(Class<?> mockedType, boolean accessible) {
        for (Constructor<?> constructor : mockedType.getDeclaredConstructors()) {
            constructor.setAccessible(accessible);
        }
    }


    private Class<?>[] prepend(Class<?> first, Class<?>... rest) {
        Class<?>[] all = new Class<?>[rest.length + 1];
        all[0] = first;
        System.arraycopy(rest, 0, all, 1, rest.length);
        return all;
    }

    public static class ClassWithSuperclassToWorkAroundJavassistBug { }
}
