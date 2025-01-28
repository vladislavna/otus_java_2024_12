package homework;

import homework.customAnnotations.After;
import homework.customAnnotations.Before;
import homework.customAnnotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.SimpleCustomerTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ResearchReflection {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCustomerTest.class);

    public static void main(String[] args) throws ClassNotFoundException {
        String nameOfTestClass = "test.SimpleCustomerTest";
        runTests(nameOfTestClass);
    }

    private static void runTests(String nameClass) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(nameClass);
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodsBefore = new ArrayList<>();
        List<Method> methodsTest = new ArrayList<>();
        List<Method> methodsAfter = new ArrayList<>();
        AtomicInteger testIntSunny = new AtomicInteger();
        AtomicInteger testIntRainy = new AtomicInteger();

        Arrays.stream(methods).forEach(method ->
                {
                    if (method.getAnnotation(Before.class) != null) {
                        methodsBefore.add(method);
                    } else if (method.getAnnotation(Test.class) != null) {
                        methodsTest.add(method);
                    } else if (method.getAnnotation(After.class) != null) {
                        methodsAfter.add(method);
                    }
                }
        );
        if (!methodsTest.isEmpty()) {
            methodsTest
                    .stream()
                    .forEach(method -> {
                                Object inst;
                                try {
                                    inst = clazz.getDeclaredConstructor().newInstance();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                logger.atInfo()
                                        .setMessage("inst.hashCode(): {}")
                                        .addArgument(() -> inst.hashCode())
                                        .log();
                                runBeforeOrAfter(inst, methodsBefore);
                                try {
                                    method.invoke(inst);
                                    testIntSunny.getAndIncrement();
                                } catch (Exception e) {
                                    testIntRainy.getAndIncrement();
                                    logger.atInfo()
                                            .setMessage("Test crashed: {}")
                                            .addArgument(() -> method.getName())
                                            .log();
                                }
                                runBeforeOrAfter(inst, methodsAfter);
                            }
                    );
        }
        logger.info("@All: " + methodsTest.size());
        logger.info("@Sunny: " + testIntSunny);
        logger.info("@Rainy: " + testIntRainy);
    }

    private static void runBeforeOrAfter(Object inst,
                                         List<Method> methodsAdditional) {
        if (!methodsAdditional.isEmpty()) {
            methodsAdditional
                    .stream()
                    .forEach(method -> {
                                try {
                                    method.invoke(inst);
                                } catch (Exception e) {
                                    logger.atInfo()
                                            .setMessage("Test crashed: {}")
                                            .addArgument(() -> method.getName())
                                            .log();
                                }
                            }
                    );
        }
    }
}