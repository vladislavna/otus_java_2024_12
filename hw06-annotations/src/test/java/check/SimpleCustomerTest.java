package check;

import static org.assertj.core.api.Assertions.assertThat;

import homework.customannotations.After;
import homework.customannotations.Before;
import homework.customannotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("squid:S2187")
public class SimpleCustomerTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCustomerTest.class);
    private int expectedId;
    private int actualId;

    @Before
    public void testBefore() {
        expectedId = 1;
        actualId = 1;
        logger.info("@Before: before");
    }

    @Test
    public void runTestFirst() {
        assertThat(expectedId).isEqualTo(actualId);
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
        assertThat(expectedId).isEqualTo(actualId);
        logger.info("@Test: start runTestThird");
        logger.info("@Test: end runTestThird");
    }

    @After
    public void testAfter() {
        logger.info("@After: after");
    }
}
