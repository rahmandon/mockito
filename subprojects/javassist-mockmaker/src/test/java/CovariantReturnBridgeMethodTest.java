import javassist.util.proxy.MethodHandler;
import org.junit.Test;
import org.mockito.plugin.javassist.ProxyFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.mockito.internal.util.StringJoiner.join;


public class CovariantReturnBridgeMethodTest {

    public interface Iterator { }
    public interface ListIterator extends Iterator { }

    public interface Iterable {
        Iterator iterator();
    }

    public interface ListIterable extends Iterable {
        ListIterator iterator();
    }

    public static class RealListIterable implements ListIterable {
        public ListIterator iterator() {
            return null;
        }
    }

    @Test
    public void test_real_object_read_compiled_by_JDK_do_have_bridge_method_for_covariant_return() throws Exception {
        displayMethodCharacteristics(RealListIterable.class);

        System.out.println(((Iterable) RealListIterable.class.newInstance()).iterator());
        System.out.println(((ListIterable) RealListIterable.class.newInstance()).iterator());
    }

    @Test
    public void proxyFactory_does_not_generate_bridge_method() throws Exception {
        ListIterable proxy = createJavassistProxy(ListIterable.class);

        displayMethodCharacteristics(proxy.getClass());

        System.out.println(((Iterable) proxy).iterator());
        System.out.println(((ListIterable) proxy).iterator());
    }

    private void displayMethodCharacteristics(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if(method.getName().contains("iterator"))
                System.out.println(join(
                        "Modifiers : " + Modifier.toString(method.getModifiers()),
                        "Returned type : " + method.getReturnType(),
                        "Name :" + method.getName(),
                        "Declared in : " + method.getDeclaringClass(),
                        "Bridge : " + (method.isBridge() ? "yes" : "no"),
                        "Synthetic : " + (method.isSynthetic() ? "yes" : "no")
                ));
        }
    }

    private <T> T createJavassistProxy(Class<T> clazz) throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[] { clazz });
        return (T) proxyFactory.create(new Class[0], new Object[0], new MethodHandler() {
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                return null;
            }
        });
    }
}
