// a program to check if the first, second, or third number is the largest of the three.

import java.util.Scanner;
public class LargestNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number1 = scanner.nextInt();
        int number2 = scanner.nextInt();
        int number3 = scanner.nextInt();

        // here we arechecking if the first number is the largest
        if (number1>number2 && number1>number3) {
            System.out.println("Is the first number the largest? true");
        } else {
            System.out.println("Is the first number the largest? false");
        }

        // here we are checking if the second number is the largest
        if (number2>number1 && number2>number3) {
            System.out.println("Is the second number the largest? true");
        } else {
            System.out.println("Is the second number the largest? false");
        }

        // also here we are checking if the third number is the largest
        if (number3>number1 && number3>number2) {
            System.out.println("Is the third number the largest? true");
        } else {
            System.out.println("Is the third number the largest? false");
        }
        scanner.close();
    }
}