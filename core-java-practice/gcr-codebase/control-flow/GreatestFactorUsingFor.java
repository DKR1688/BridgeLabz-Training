// a program to print the greatest factor of a number beside itself using a loop.

import java.util.*;
public class GreatestFactorUsingFor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // an integer input and assign it to the number variable. 
        // As well as define a greatestFactor variable and assign it to 1
        int number = scanner.nextInt();
        int greatestFactor = 1;

        // Create a for loop that runs from last 
        // but one till 1 as in i = number - 1 to i = 1.
        for (int i=number-1; i>=1; i--) {
            if (number%i == 0) {
                greatestFactor = i;
                break;
            }
        }
        System.out.println("The greatest factor of "+number+" is "+greatestFactor);
        scanner.close();
    }
}