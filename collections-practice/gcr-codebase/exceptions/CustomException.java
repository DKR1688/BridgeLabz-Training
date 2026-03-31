import java.util.*;
//custom class to custom exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class CustomException {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try {
            System.out.print("Enter your age- ");
            int age =sc.nextInt();
            validateAge(age);
            
        } catch (InvalidAgeException e) {
            //handling custom exception
            System.out.println(e.getMessage());
        } catch (Exception e) {
            //handling any other unexpected exceptions
            System.out.println("Invalid input!");
        } finally {
            sc.close();
        }
    }
	
	static void validateAge(int age) throws InvalidAgeException {
        if (age<18) {
            throw new InvalidAgeException("Age must be 18.");
        } else {
            System.out.println("Access granted.");
        }
    }
}
