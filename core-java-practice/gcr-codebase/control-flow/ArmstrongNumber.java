// a program to check if a number is armstrong or not. 
// Use the hints to show the steps clearly in the code

import java.util.*;
public class ArmstrongNumber {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        int number = scanner.nextInt();
        int sum = 0;
        int originalNumber=number;

        // In the while loop find each digit which is the reminder of the modulus operation number % 10. 
        // Finding the cube of the number and add it to the sum variable
        while (originalNumber!=0) {

            // quotient of the number using the division operation number/10 
            // and assign it to the original number. This is removing the last digit of the original number.

            int digit = originalNumber % 10;
            sum += Math.pow(digit, 3);
            originalNumber /= 10;
        }

        if (sum==number) {
            System.out.println(number+" is an Armstrong number.");
        } else {
            System.out.println(number+" is not an Armstrong number.");
        }
        scanner.close();
    }
}