import java.util.*;
public class ExceptionPropagation {
	public static void main(String[] args) {
		try {
			double interest1 =calculateInterest(1000, 3, 2);
			System.out.println("Interest is- "+interest1);

			double interest2 =calculateInterest(5000, -1, 2);
			System.out.println("Interest is- "+interest2);

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid input: Amount and rate must be positive.");
		}
	}

	//a method calculateInterest(double amount, double rate, int years)
	public static double calculateInterest(double amount, double rate, int years) throws IllegalArgumentException {
		if (amount<0 || rate<0) {
			throw new IllegalArgumentException("Amount and rate can not be negative.");
		}
		return (amount*rate*years)/100;
	}
}
