package methods;

import java.util.*;
public class NumberAnalysis{
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        int[] numbers = new int[5];
        System.out.println("Enter the 5 numbers ");
        for (int i=0; i<numbers.length; i++) {
            numbers[i] =scanner.nextInt();
        }

        // Loop through the array using the length call the method isPositive() and if positive call method isEven() 
        for (int i=0; i<numbers.length; i++) {
            int num = numbers[i];
            if (isPositive(num)) {
                if (isEven(num)) {
                    System.out.println(num+" is positive and even number");
                } else {
                    System.out.println(num + " is positive and odd number");
                }
            } else {
                System.out.println(num + " is negative number");
            }
        }

        //compare the first and last element of the array by calling the method compare() and display if they are equal, greater, or less
        int ans= compare(numbers[0], numbers[numbers.length-1]);
        if (ans==1) {
            System.out.println(numbers[0]+" is greater than "+numbers[numbers.length-1]);
        } else if (ans==0) {
            System.out.println(numbers[0]+" is equal to "+numbers[numbers.length-1]);
        } else {
            System.out.println(numbers[0]+" is less than "+numbers[numbers.length-1]);
        }
        scanner.close();
    }

    //a Method to Check whether the number is positive or negative
    static boolean isPositive(int num) {
    	if(num<0) {
    		return false;
    	}
        return num>0;
    }

    //a Method to check whether the number is even or odd
    static boolean isEven(int num) {
    	if(num%2!=0) {
    		return false;
    	}
        return num%2 == 0;
    }

    //a Method to compare two numbers and return 1 if number1 > number2 or 0 if both are equal or -1 if number1 < number2 
    public static int compare(int num1, int num2) {
        if (num1>num2) {
            return 1;
        } else if (num1==num2) {
            return 0;
        } else {
            return -1;
        }
    }
}
