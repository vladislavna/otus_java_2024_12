package ru.otus.appcontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.exceptions.DependencyInjectionException;

@SuppressWarnings({"squid:S1068", "java:S3011"})
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> methods = getMethods(configClass);
        Object configClassInstance = getConfigClassInstance(configClass);
        for (Method method : methods) {
            String qualifierName =
                    method.getDeclaredAnnotation(AppComponent.class).name();
            if (!appComponentsByName.containsKey(qualifierName)) {
                Object[] parameters = getParameters(method);
                createBean(method, qualifierName, configClassInstance, parameters);
            } else {
                throw new DependencyInjectionException("Обнаружен дубликат бина");
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object appComponent = null;
        for (Map.Entry<String, Object> entry : appComponentsByName.entrySet()) {
            Object value = entry.getValue();
            if (componentClass.isInstance(value)) {
                if (appComponent != null) {
                    throw new DependencyInjectionException("Найдено несколько компонентов");
                }
                appComponent = value;
            }
        }
        return (C) Objects.requireNonNull(appComponent);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        Object appComponent = appComponentsByName.get(componentName);
        return (C) Objects.requireNonNull(appComponent);
    }

    private List<Method> getMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted((Comparator.comparingInt(method ->
                        method.getDeclaredAnnotation(AppComponent.class).order())))
                .toList();
    }

    private Object getConfigClassInstance(Class<?> configClass) {
        try {
            Constructor<?> constructor = configClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException e) {
            throw new DependencyInjectionException(e.getMessage());
        }
    }

    private Object[] getParameters(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return parameterTypes;
        }
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = getAppComponent(parameterTypes[i]);
        }
        return parameters;
    }

    private void createBean(Method method, String name, Object instance, Object... args) {
        method.setAccessible(true);
        try {
            Object bean = method.invoke(instance, args);
            appComponentsByName.put(name, bean);
            appComponents.add(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DependencyInjectionException(e.getMessage());
        }
    }
}
