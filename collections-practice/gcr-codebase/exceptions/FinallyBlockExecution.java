import java.util.*;
public class FinallyBlockExecution {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        System.out.print("Enter first number- ");
        int num1 =sc.nextInt();

        System.out.print("Enter second number- ");
        int num2 =sc.nextInt();
        try {
            int ans =num1/num2;
            System.out.println("Division of numbers is- " +ans);
        } catch (ArithmeticException e) {
            System.out.println("We can not divide by zero.");
        } finally {
            System.out.println("Operation completed.");
        }
        sc.close();
    }
}
