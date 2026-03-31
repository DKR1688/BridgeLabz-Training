// a program to take user input for 5 numbers and 
// check whether a number is positive,  negative, or zero. 
// Further for positive numbers check if the number is even or odd. 
// Finally compare the first and last elements of the array and 
// display if they equal, greater or less

import java.util.*;
public class PositiveNegativeNumber {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = new int[5];

        // here we are taking input for 5 numbers
        for (int index=0; index<numbers.length; index++) {
            numbers[index] = scanner.nextInt();
        }

        // here we are checking whether the number is positive, negative or zero
        for (int index=0; index<numbers.length; index++) {
            if(numbers[index]>0){
                if(numbers[index]%2==0){
                    System.out.println(numbers[index]+" is a positive even number.");
                } else {
                    System.out.println(numbers[index]+" is a positive odd number.");
                }

            } else if(numbers[index]<0){
                System.out.println(numbers[index]+" is a negative number.");
            } else {
                System.out.println(numbers[index]+" is zero.");
            }

            int firstNumber=numbers[0];
            int lastNumber=numbers[numbers.length-1];

            if(firstNumber==lastNumber){
                System.out.println("The first and last number are equal.");
            } else if(firstNumber>lastNumber){
                System.out.println("The first number is greater than the last number.");
            } else {
                System.out.println("The first number is less than the last number.");
            }
            
        }
        scanner.close();
    }
}