// a program to create a basic calculator for addition, subtraction, multiplication, and division. 
// The program should ask for two numbers (floating point) and perform all the operations
import java.util.Scanner;
public class ArithmeticOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a variable number1 and number2 and take user inputs.
        float number1 = scanner.nextFloat();
        float number2 = scanner.nextFloat();

        float addition = number1 + number2;
        float subtraction = number1 - number2;
        float multiplication = number1 * number2;
        float division = (float)number1 / number2;

        System.out.println("The addition, subtraction, multiplication and division of 2 numbers "+number1+" and "+number2+" is "+addition+", "+subtraction+", "+multiplication+" and "+division);
        scanner.close();
    }
    
}
