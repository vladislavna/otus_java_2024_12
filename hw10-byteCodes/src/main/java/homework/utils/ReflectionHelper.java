package homework.utils;

import homework.model.MethodPreview;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionHelper {

    public static Set<MethodPreview> findMethodsByAnnotation(
            Object objClass, Class<? extends java.lang.annotation.Annotation> logClass) {
        return Arrays.stream(objClass.getClass().getDeclaredMethods())
                .filter(method -> checkAnnotation(method, logClass))
                .map(method -> buildMethodPreview(method))
                .collect(Collectors.toSet());
    }

    protected static boolean checkAnnotation(Method method, Class<? extends java.lang.annotation.Annotation> logClass) {
        return method.isAnnotationPresent(logClass);
    }

    public static MethodPreview buildMethodPreview(Method method) {
        return MethodPreview.builder()
                .name(method.getName())
                .params(method.getParameterTypes())
                .build();
    }
}
