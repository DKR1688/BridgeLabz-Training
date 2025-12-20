// a program to find the power of a number.

import java.util.*;
public class PowerOfNumberUsingFor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get integer input for two variables - number and power 
        int number= scanner.nextInt();
        int power = scanner.nextInt();

        // check for positive
        if (number<0 || power<0) {
            System.out.println("Number and power should be non-negative");
            scanner.close();
            return;
        }
        scanner.close();

        int ans= 1;

        // In each iteration of the loop, multiply the result by the number 
        // and assign the value to the result. Finally, print the result
        for (int i=1; i<=power; i++) {
            ans = ans*number;
        }
        System.out.println("The power of number is " +ans);
        scanner.close();
    }
}