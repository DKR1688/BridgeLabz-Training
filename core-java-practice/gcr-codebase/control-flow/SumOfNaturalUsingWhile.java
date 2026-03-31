// a program to find the sum of n natural numbers using while loop compare the result with the formulae n*(n+1)/2 
// and show the result from both computations was correct. 

 import java.util.*;
 public class SumOfNaturalUsingWhile {
 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
 
        // Checking if the number is a natural number
        if (number > 0) {

            // Computing the sum using while loop
            int sum= 0;
            int i = 1;
            while (i <= number) {
                sum+= i;
                i++;
            }
            System.out.println("Sum using while loop: " + sum);
 
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