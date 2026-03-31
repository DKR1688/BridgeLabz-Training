import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Author {
    String name();
}

@Author(name = "Author Name")
class AnnotatedClass {}

public class RetrieveAnnotationsAtRuntime {
    public static void main(String[] args) {
        Class<?> clazz = AnnotatedClass.class;
        if (clazz.isAnnotationPresent(Author.class)) {
            Author author = clazz.getAnnotation(Author.class);
            System.out.println("Author: " + author.name());
        } else {
            System.out.println("No Author annotation found.");
        }
    }
}

