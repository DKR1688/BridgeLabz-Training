// a program to print odd and even numbers between 1 to the number entered by the user

import java.util.Scanner;
public class OddAndEvenNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // user input
        int number = scanner.nextInt();

        // a number is natural or not
        if (number < 1) {
            System.out.println("It is not a natural number.");
        } else {

            // here we go from 1 to number for checking odd and even
            for (int i = 1; i <= number; i++) {
                if (i % 2 == 0) {
                    System.out.println(i+" is an even number.");
                } else {
                    System.out.println(i+" is an odd number.");
                }
            }
        }
        scanner.close();
    }

}