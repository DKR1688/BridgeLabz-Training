import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface TaskInfo {
    String priority();
    String assignedTo();
}

class TaskManager {
    @TaskInfo(priority = "HIGH", assignedTo = "Alice")
    public void taskA() {}

    @TaskInfo(priority = "LOW", assignedTo = "Bob")
    public void taskB() {}
}

public class CustomTaskInfoAnnotation {
    public static void main(String[] args) throws Exception {
        for (Method m : TaskManager.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(TaskInfo.class)) {
                TaskInfo info = m.getAnnotation(TaskInfo.class);
                System.out.println(m.getName() + ": Priority=" + info.priority() + ", AssignedTo=" + info.assignedTo());
            }
        }
    }
}

