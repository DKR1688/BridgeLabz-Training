import java.util.*;
public class PrimeNumberChecker {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the number - ");
        int number = scanner.nextInt();

        boolean ans= isPrime(number);

        if (ans) {
            System.out.println("It is a prime number.");
        } else {
            System.out.println("It is not a prime number.");
        }
        scanner.close();
    }

    // this function to check if a number is prime
    public static boolean isPrime(int number) {
        if (number<=1) {
            return false;
        }
        if (number==2) {
            return true;
        }

        //here we will check divisibility of number up to sqrt
        for (int i=2; i<=Math.sqrt(number); i++) {
            if (number%i == 0) {
                return false;
            }
        }
        return true;
    }
}