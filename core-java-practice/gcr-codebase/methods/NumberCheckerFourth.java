package methods;

import java.util.*;
public class NumberCheckerFourth {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the number ");
        int number =scanner.nextInt();

        System.out.println("It is a prime number or not - " +isPrime(number));
        System.out.println("It is a neon number or not - " +isNeon(number));
        System.out.println("It is a spy number or not - " +isSpy(number));
        System.out.println("It is an automorphic number or not - " +isAutomorphic(number));
        System.out.println("It is a buzz number or not - " +isBuzz(number));
        scanner.close();
    }

    //Method to Check if a number is prime number. 
    //A prime number is a number greater than 1 that has no positive divisors other than 1 and itself. 
    static boolean isPrime(int num) {
        if (num<=1) {
        	return false;
        }
        for (int i=2; i<=Math.sqrt(num); i++) {
            if (num%i == 0) {
            	return false;
            }
        }
        return true;
    }

    //Method to Check if a number is a neon number. 
    //A neon number is a number where the sum of digits of the square of the number is equal to the number itself 
    static boolean isNeon(int num) {
        int square = num*num;
        int sum = 0;
        while (square>0) {
            sum += square % 10;
            square/=10;
        }
        return sum == num;
    }

    //Method to Check if a number is a spy number. 
    //A number is called a spy number if the sum of its digits is equal to the product of its digits
    static boolean isSpy(int num) {
        int sum=0;
        int product=1;
        int temp = num;
        while (temp>0) {
            int digit = temp%10;
            sum += digit;
            product *= digit;
            temp/=10;
        }
        return sum == product;
    }

    //Method to Check if a number is an automorphic number. 
    //An automorphic number is a number whose square ends with the number itself. E.g. 5 is an automorphic number
    static boolean isAutomorphic(int num) {
        int square = num*num;
        return String.valueOf(square).endsWith(String.valueOf(num));
    }

    //Method to Check if a number is a buzz number. 
    //A buzz number is a number that is either divisible by 7 or ends with 7
    static boolean isBuzz(int num) {
        return (num%7 == 0) || (num%10 == 7);
    }
}