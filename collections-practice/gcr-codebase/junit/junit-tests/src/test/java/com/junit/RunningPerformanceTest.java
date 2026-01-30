package com.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class RunningPerformanceTest {
	@Test
    @Timeout(2)  //test will be failed if execution takes more than 2 seconds
    void testLongRunningTaskTimeout() {
        String result =RunningPerformance.longRunningTask();
        assertEquals("Task Completed", result);
    }
}
