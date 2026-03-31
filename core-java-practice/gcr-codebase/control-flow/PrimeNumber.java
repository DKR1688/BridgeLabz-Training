// a Program to check if the given number is a prime number or not

import java.util.*;
public class PrimeNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number =scanner.nextInt();

        // A number that can be divided exactly only by itself and 1 are Prime Numbers,
        // Prime Numbers checks are done for numbers greater than 1
        if (number<=1) {
            System.out.println("It is not a prime number.");
            scanner.close();
            return;
        }

        // the isPrime boolean variable to store the result

        boolean isPrime = true;

        // Loop through all the numbers from 2 to the user input number and 
        // check if the reminder is zero. If the reminder is zero break out 
        // from the loop as the number is divisible by some other number and is not a prime number.
        for (int i=2; i<=Math.sqrt(number); i++) {
            if (number%i == 0) {
                isPrime = false;
                break;
            }
        }

        if (isPrime) {
            System.out.println(number+" is a prime number.");
        } else {
            System.out.println(number+" is not a prime number.");
        }
        scanner.close();
    }
}