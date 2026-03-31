import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Inject {}

class Service {}

class Consumer {
    @Inject
    private Service service;
    public Service getService() { return service; }
}

class SimpleDIContainer {
    public static void injectDependencies(Object obj) throws Exception {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Object dependency = field.getType().getDeclaredConstructor().newInstance();
                field.set(obj, dependency);
            }
        }
    }
}

public class DependencyInjectionUsingReflection {
    public static void main(String[] args) throws Exception {
        Consumer consumer = new Consumer();
        SimpleDIContainer.injectDependencies(consumer);
        System.out.println("Service injected: " + (consumer.getService() != null));
    }
}

