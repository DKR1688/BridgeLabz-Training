package com.junit;

public class RunningPerformance {
	public static String longRunningTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Task Completed";
    }
}
