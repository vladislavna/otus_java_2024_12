package homework;

import homework.proxy.Ioc;

public class ProxyDemo {
    public static void main(String[] args) {
        TestLoggingInterface myClass = Ioc.createMyClass();
        myClass.calculation(1);
        myClass.calculation(2, 3);
        myClass.calculation(4, 5, "Test");
    }
}
