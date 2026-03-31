
public class PasswardStrengthValidator {
    public static void main(String[] args) {
        String password = "Secure@123";

        if (SecurityUtils.isStrongPassword(password)) {
            System.out.println("Strong password");
        } else {
            System.out.println("Weak password");
        }
    }
}

interface SecurityUtils {
     static  boolean isStrongPassword(String password) {

        if (password==null || password.length() < 8) {
            return false;
        }

        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]{8,}$");
    }
}
