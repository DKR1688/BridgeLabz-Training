// a program to find all the multiples of a number below 100 using while loop.

import java.util.*;
public class MultiplesOfNumberUsingWhile {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Getting the input value for a variable named number. 
        int number = scanner.nextInt();

        // Checking the number is a positive integer and less than 100.
        if (number<=0 || number>=100) {
            System.out.println("Number is out of range.");
            scanner.close();
            return;
        }
        scanner.close();
 
        System.out.println("Multiples of " + number + " below 100 are:");
        int counter = 100;

        // we are using while loop to iterate from 100 to 1.
        while (counter >= 1) {
            if (counter % number == 0) {
                System.out.println(counter);
            }
            counter--;
        }
        scanner.close();
    }
}