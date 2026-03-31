package methods;

import java.util.*;
public class NumberCheckerThird {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number ");
        int number =scanner.nextInt();

        int count = countDigits(number);
        System.out.println("Count of digits are " + count);

        int[] digits = getDigits(number);
        System.out.print("Digits of the array are ");
        for (int digit :digits) {
            System.out.print(digit + " ");
        }
        System.out.println();

        int[] reverseDigits = reverseArray(digits);
        System.out.print("Reversed array is ");
        for (int digit : reverseDigits) {
            System.out.print(digit+ " ");
        }
        System.out.println();

        System.out.println("Are original and reversed arrays equal? " +areArraysEqual(digits, reverseDigits));
        System.out.println("It is a palindrome number or not - " +isPalindrome(digits));
        System.out.println("It is a duck number or not - " +isDuckNumber(digits));
        scanner.close();
    }

    //Method to find the count of digits in the number and 
    //a Method to Store the digits of the number in a digits array
    static int countDigits(int num) {
        return String.valueOf(num).length();
    }

    static int[] getDigits(int num) {
        String string =String.valueOf(num);
        int[] digits = new int[string.length()];
        for (int i=0; i<string.length(); i++) {
            digits[i] = string.charAt(i)-'0';
        }
        return digits;
    }

    //Method to reverse the digits array 
    static int[] reverseArray(int[] digits) {
        int[] reverseDigit = new int[digits.length];
        for (int i=0; i<digits.length; i++) {
            reverseDigit[i] = digits[digits.length-1-i];
        }
        return reverseDigit;
    }

    //Method to compare two arrays and check if they are equal
    static boolean areArraysEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
        	return false;
        }
        for (int i=0; i<arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
            	return false;
            }
        }
        return true;
    }

    //Method to check if a number is a palindrome using the Digits. 
    //A palindrome number is a number that remains the same when its digits are reversed. 
    static boolean isPalindrome(int[] digits) {
        int[] reverseDigit = reverseArray(digits);
        return areArraysEqual(digits, reverseDigit);
    }

    //Method to Check if a number is a duck number using the digits array. 
    //A duck number is a number that has a non-zero digit present in it
    public static boolean isDuckNumber(int[] digits) {
        if (digits[0] == 0) {
        	return false;
        }
        for (int digit : digits) {
            if (digit == 0) {
            	return true;
            }
        }
        return false;
    }
}