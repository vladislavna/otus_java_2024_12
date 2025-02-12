package homework.impl;

import homework.TestLoggingInterface;
import homework.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.aop.proxy.MyClassImpl;

public class TestLoggingImpl implements TestLoggingInterface {
    private static final Logger logger = LoggerFactory.getLogger(MyClassImpl.class);

    @Log
    public void calculation(int first) {
        logger.info("Case 1: one param - {}", first);
    }

    public void calculation(int first, int second) {
        logger.info("Case 2: two params - {}, {}", first, second);
    }

    @Log
    public void calculation(int first, int second, String third) {
        logger.info("Case 3: three params - {}, {}, {}", first, second, third);
    }
}
