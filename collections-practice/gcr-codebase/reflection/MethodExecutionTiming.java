import java.lang.reflect.*;

class Task {
    public void runTask() {
        for (int i = 0; i < 1000000; i++); // Simulate work
    }
}

public class MethodExecutionTiming {
    public static void main(String[] args) throws Exception {
        Task task = new Task();
        Method method = Task.class.getMethod("runTask");
        long start = System.nanoTime();
        method.invoke(task);
        long end = System.nanoTime();
        System.out.println("Execution time (ns): " + (end - start));
    }
}

