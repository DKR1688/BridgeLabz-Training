// a Program to find the factorial of an integer entered by the user.

import java.util.Scanner;
public class FactorialOfIntegerWhileLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        // here if the number is a positive integer
        if (number>=0) {

            // we are calculating the factorial using while loop
            int factorial=1;
            int i=1;
            while (i<=number) {
                factorial*=i;
                i++;
            }

            // printing the factorial value
            System.out.println("Factorial of "+number+" is "+factorial);
        } else {
            System.out.println("It is a negative integer.");
        }
        scanner.close();
    }
}

