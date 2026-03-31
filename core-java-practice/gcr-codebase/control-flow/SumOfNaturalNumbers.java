// a program to check for the natural number and write the sum of n natural numbers 

import java.util.Scanner;
public class SumOfNaturalNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        // checking if the number is a natural number
        if (number>=1) {

            // calculating the sum of n natural numbers using the formula n(n+1)/2
            int sum = number*(number+1)/2;
            System.out.println("The sum of "+number+" natural numbers is "+sum);
        } else {
            System.out.println("The number "+number+" is not a natural number");
        }
        scanner.close();
    }
    
}
