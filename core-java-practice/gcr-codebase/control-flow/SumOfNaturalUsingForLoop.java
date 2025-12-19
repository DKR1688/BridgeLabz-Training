// a program with the for loop instead of a while loop to find the sum of n Natural Numbers. 

import java.util.*;
 public class SumOfNaturalUsingForLoop {
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
 
        // Checking if the number is a natural number
        if (number > 0) {

            // Computing the sum using for loop
            int sum= 0;
            for (int i = 1; i <= number; i++) {
                sum+= i;
            }
            System.out.println("Sum using for loop: " + sum);
 
            // here we are computing the sum using formula n*(n+1)/2
            int sumUsingFormula = number * (number + 1) / 2;
            System.out.println("Sum using formula: " + sumUsingFormula);
 
            // Comparing the both results
            if (sum == sumUsingFormula) {
                System.out.println("Both results are correct and equal.");
            } else {
                System.out.println("There is a difference between the both results.");
            }
        }else {
            System.out.println("It's not a natural number");
        }
        scanner.close();
    }
 }