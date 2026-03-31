import java.lang.reflect.*;

interface Greeting {
    void sayHello(String name);
}

class GreetingImpl implements Greeting {
    public void sayHello(String name) {
        System.out.println("Hello, " + name + "!");
    }
}

class LoggingProxy implements InvocationHandler {
    private final Object target;
    public LoggingProxy(Object target) { this.target = target; }
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoking method: " + method.getName());
        return method.invoke(target, args);
    }
}

public class CustomLoggingProxyUsingReflection {
    public static void main(String[] args) {
        Greeting greeting = (Greeting) Proxy.newProxyInstance(
            Greeting.class.getClassLoader(),
            new Class[]{Greeting.class},
            new LoggingProxy(new GreetingImpl())
        );
        greeting.sayHello("World");
    }
}

