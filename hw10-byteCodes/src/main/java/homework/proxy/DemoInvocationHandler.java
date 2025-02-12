package homework.proxy;

import homework.annotations.Log;
import homework.model.MethodPreview;
import homework.utils.ReflectionHelper;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DemoInvocationHandler<T> implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);
    private final T myClass;
    private final Set<MethodPreview> myClassMethods;

    public DemoInvocationHandler(T myClass) {
        this.myClass = myClass;
        this.myClassMethods = ReflectionHelper.findMethodsByAnnotation(myClass, Log.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (myClassMethods.contains(ReflectionHelper.buildMethodPreview(method))) {
            logger.info("executed method: {}, param: {}", method.getName(), args);
        }
        return method.invoke(myClass, args);
    }
}
