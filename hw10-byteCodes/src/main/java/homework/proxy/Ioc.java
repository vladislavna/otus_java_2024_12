package homework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Ioc {

    private Ioc() {
        throw new IllegalStateException("Utility class");
    }

    public static Object createMyClass(Object myClass) {
        InvocationHandler handler = new DemoInvocationHandler<>(myClass);
        return Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), myClass.getClass().getInterfaces(), handler);
    }
}
