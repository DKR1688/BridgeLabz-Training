// a Program to find the factorial of an integer entered by the user using for loop

import java.util.Scanner;
public class FactorialOfIntegerForLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        // here if the number is a positive integer
        if (number>=0) {

            // we are calculating the factorial using for loop
            int factorial=1;
            for (int i=1; i<=number; i++) {
                factorial*=i;
            }

            // printing the factorial value
            System.out.println("Factorial of "+number+" is "+factorial);
        } else {
            System.out.println("It is a negative integer.");
        }
        scanner.close();
    }
}
