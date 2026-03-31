import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Todo {
    String task();
    String assignedTo();
    String priority() default "MEDIUM";
}

class Project {
    @Todo(task = "Implement login", assignedTo = "Alice")
    public void login() {}

    @Todo(task = "Add logout", assignedTo = "Bob", priority = "HIGH")
    public void logout() {}
}

public class TodoAnnotation {
    public static void main(String[] args) {
        for (Method m : Project.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Todo.class)) {
                Todo todo = m.getAnnotation(Todo.class);
                System.out.println(m.getName() + ": Task=" + todo.task() + ", AssignedTo=" + todo.assignedTo() + ", Priority=" + todo.priority());
            }
        }
    }
}

