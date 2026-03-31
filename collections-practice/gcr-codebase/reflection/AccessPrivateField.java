import java.lang.reflect.*;

class Person {
    private int age = 25;
}

public class AccessPrivateField {
    public static void main(String[] args) throws Exception {
        Person p = new Person();
        Field ageField = Person.class.getDeclaredField("age");
        ageField.setAccessible(true);
        System.out.println("Original age: " + ageField.get(p));
        ageField.set(p, 30);
        System.out.println("Modified age: " + ageField.get(p));
    }
}

