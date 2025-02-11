package homework;

import homework.impl.TestLoggingImpl;
import homework.proxy.Ioc;

public class ProxyDemo {
    public static void main(String[] args) {
        TestLoggingInterface myClass = (TestLoggingInterface) Ioc.createMyClass(new TestLoggingImpl());
        myClass.calculation(1);
        myClass.calculation(2, 3);
        myClass.calculation(4, 5, "Test");
    }
}
