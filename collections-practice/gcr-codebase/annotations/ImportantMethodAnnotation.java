import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface ImportantMethod {
    String level() default "HIGH";
}

class Demo {
    @ImportantMethod
    public void methodA() {}

    @ImportantMethod(level = "LOW")
    public void methodB() {}

    public void methodC() {}
}

public class ImportantMethodAnnotation {
    public static void main(String[] args) {
        for (Method m : Demo.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ImportantMethod.class)) {
                ImportantMethod imp = m.getAnnotation(ImportantMethod.class);
                System.out.println(m.getName() + ": level=" + imp.level());
            }
        }
    }
}

