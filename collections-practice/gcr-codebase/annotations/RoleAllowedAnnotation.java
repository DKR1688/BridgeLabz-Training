import java.lang.annotation.*;
import java.lang.reflect.*;

@Retention(RetentionPolicy.RUNTIME)
@interface RoleAllowed {
    String value();
}

class SecureService {
    @RoleAllowed("ADMIN")
    public void adminTask() {
        System.out.println("Admin task executed.");
    }
}

public class RoleAllowedAnnotation {
    public static void main(String[] args) throws Exception {
        String userRole = "USER"; // Change to "ADMIN" to allow access
        SecureService service = new SecureService();
        Method m = SecureService.class.getDeclaredMethod("adminTask");
        RoleAllowed roleAllowed = m.getAnnotation(RoleAllowed.class);
        if (roleAllowed != null && roleAllowed.value().equals(userRole)) {
            m.invoke(service);
        } else {
            System.out.println("Access Denied!");
        }
    }
}

