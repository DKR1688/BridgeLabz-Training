// a program FizzBuzz, take a number as user input and if it is a positive integer loop 
// from 0 to the number and save the number, but for multiples of 3 save "Fizz" instead 
// of the number, for multiples of 5 save "Buzz", and for multiples of both save "FizzBuzz". 
// Finally, print the array results for each index position in the format Position 1 = 1, …, Position 3 = Fizz,...

import java.util.Scanner;

public class FizzBuzz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a positive number ");
        int number =scanner.nextInt();

        if (number<=0) {
            System.out.println("It is a negative number");
            scanner.close();
            return;
        }

        // Create a String array to store results
        String[] ans = new String[number + 1];

        for (int i=0; i<=number; i++) {
            if (i%3 == 0 && i%5 == 0 && i!=0) {
                ans[i] = "FizzBuzz";
            } else if (i%3 == 0 && i != 0) {
                ans[i] = "Fizz";
            } else if (i%5 == 0 && i != 0) {
                ans[i] = "Buzz";
            } else {
                ans[i] = String.valueOf(i);
            }
        }

        // loop again to show the results of the array based on the index positio
        System.out.println("\nFizzBuzz Results:");
        for (int i=0; i<=number; i++) {
            System.out.println("Position "+i+" = " + ans[i]);
        }

        scanner.close();
    }
}