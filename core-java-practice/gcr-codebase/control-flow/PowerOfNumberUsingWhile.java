// a program to find the power of a number using a while loop.

import java.util.*;
public class PowerOfNumberUsingWhile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get integer input for two variables number and power 
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
        int counter=0;

        // In each iteration of the loop, multiply the result by the number 
        // and assign the value to the result. Finally, print the result
        // here we are using while loop
        while (counter<power) {
            ans = ans*number;
            counter++;
        }
        System.out.println("The power of number is " +ans);
        scanner.close();
    }
}