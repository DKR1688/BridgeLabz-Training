// a program to check if a number taken from the user is a Harshad Number.

import java.util.*;
public class HarshadNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int number = scanner.nextInt();
        int RealNumber =number;

        // calculating sum of digits
        int sum = 0;
        while (number>0) {
            int digit = number%10;
            sum += digit;
            number /= 10;
        }

        // Checking the original number is divisible by the sum of its digits to be a Harshad Number
        if (RealNumber%sum == 0) {
            System.out.println("It is a Harshad Number.");
        } else {
            System.out.println("It is Not a Harshad Number.");
        }
        scanner.close();
    }
}