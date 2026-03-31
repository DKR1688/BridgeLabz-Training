// a program to print the greatest factor of a number beside itself using a while loop.

import java.util.*;
public class GreatestFactorUsingWhile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get an integer input and assign it to the number variable. 
        // As well as define a greatestFactor variable and assign it to 1
        int number= scanner.nextInt();
        int greatestFactor = 1;

        // Create a variable counter and assign counter = number - 1; 
        // Use the while loop till the counter is equal to 1.
        int counter = number - 1;
        while (counter>=1) {
            if (number%counter == 0) {
                greatestFactor = counter;
                break;
            }
            counter--;
        }
        System.out.println("The greatest factor of "+number+" is "+greatestFactor);
        scanner.close();
    }
}