package homework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Ioc {

    public static Object createMyClass(Object myClass) {
        InvocationHandler handler = new DemoInvocationHandler<>(myClass);
        return Proxy.newProxyInstance(
                Ioc.class.getClassLoader(), myClass.getClass().getInterfaces(), handler);
    }
}
