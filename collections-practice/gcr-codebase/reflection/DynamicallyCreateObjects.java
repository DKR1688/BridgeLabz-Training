import java.lang.reflect.*;

class Student {
    public String name;
    public Student() {
        this.name = "Default";
    }
}

public class DynamicallyCreateObjects {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Student.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Student s = (Student) obj;
        System.out.println("Student name: " + s.name);
    }
}

