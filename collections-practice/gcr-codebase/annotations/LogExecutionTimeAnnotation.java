import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface LogExecutionTime {}

class TimerDemo {
    @LogExecutionTime
    public void fastMethod() {
        for (int i = 0; i < 10000; i++); // Fast
    }
    @LogExecutionTime
    public void slowMethod() {
        for (int i = 0; i < 10000000; i++); // Slow
    }
}

public class LogExecutionTimeAnnotation {
    public static void main(String[] args) throws Exception {
        TimerDemo demo = new TimerDemo();
        for (Method m : TimerDemo.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.nanoTime();
                m.invoke(demo);
                long end = System.nanoTime();
                System.out.println(m.getName() + " execution time: " + (end - start) + " ns");
            }
        }
    }
}

