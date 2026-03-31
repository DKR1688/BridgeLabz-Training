import java.util.*;
public class UncheckedException {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		try {
            System.out.print("Enter numerator- ");
            int numerator = sc.nextInt();

            System.out.print("Enter denominator- ");
            int denominator = sc.nextInt();

            int ans= numerator / denominator;
            System.out.println("Result is- " +ans);
            
		} catch (ArithmeticException e) {
			//If the user enters 0 as the denominator, catch and handle 
			System.out.println("Error- dividing by zero is not possible.");
		} catch (InputMismatchException e) {
			//If the user enters a non-numeric value, catch and handle 
			System.out.println("Error- please enter only numeric values.");
		} finally {
			sc.close();
		}
	}
}
