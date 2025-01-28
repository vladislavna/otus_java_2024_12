package test;

import homework.customAnnotations.After;
import homework.customAnnotations.Before;
import homework.customAnnotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCustomerTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCustomerTest.class);

    @Before
    public void testBefore() {
        logger.info("@Before: before");
    }

    @Test
    public void runTestFirst() {
        logger.info("@Test: start runTestFirst");
        logger.info("@Test: end runTestFirst");
    }

    @Test
    public void runTestSecond() {
        logger.info("@Test: start runTestSecond");
        throw new RuntimeException("We have some problem");
    }

    @Test
    public void runTestThird() {
        logger.info("@Test: start runTestThird");
        logger.info("@Test: end runTestThird");
    }

    @After
    public void testAfter() {
        logger.info("@After: after");
    }
}