// a program to find all the multiples of a number taken as user input below 100.

import java.util.*;
public class MultiplesOfNumberUsingFor {

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
 
        System.out.println("Multiples of " + number + " below 100 are:");
        // here we are running a for loop from i = 100 to i = 1.
        for (int i=100; i>=1; i--) {
            if (i%number == 0) {
                System.out.println(i);
            }
        }
        scanner.close();
    }
}