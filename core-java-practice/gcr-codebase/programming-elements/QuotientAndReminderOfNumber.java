//  a program to take 2 numbers and print their quotient and reminder
import java.util.Scanner;
public class QuotientAndReminderOfNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();

        // Use division operator (/) for quotient and moduli operator (%) for reminder
        int quotient = number1 / number2;
        int remainder = number1 % number2;

        System.out.println("The quotient is "+quotient+" and Reminider is "+remainder+" of two numbers "+number1+" and "+number2);
        scanner.close();
    }
}
