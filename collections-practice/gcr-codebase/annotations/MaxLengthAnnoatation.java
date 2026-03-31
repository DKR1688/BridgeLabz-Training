import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface MaxLength {
    int value();
}

class User {
    @MaxLength(8)
    private String username;
    public User(String username) throws Exception {
        Field field = this.getClass().getDeclaredField("username");
        MaxLength max = field.getAnnotation(MaxLength.class);
        if (max != null && username.length() > max.value()) {
            throw new IllegalArgumentException("Username exceeds max length: " + max.value());
        }
        this.username = username;
    }
    public String getUsername() { return username; }
}

public class MaxLengthAnnotation {
    public static void main(String[] args) {
        try {
            User u1 = new User("short");
            System.out.println("Username: " + u1.getUsername());
            User u2 = new User("toolongusername");
            System.out.println("Username: " + u2.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

