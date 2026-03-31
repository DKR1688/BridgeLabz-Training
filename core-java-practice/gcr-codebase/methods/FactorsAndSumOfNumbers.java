package methods;
//a program to find the factors of a number taken as user input, store the factors 
//in an array, and display the factors. Also find the sum, sum of square of factors 
//and product of the factors and display the results

import java.util.*;
public class FactorsAndSumOfNumbers {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter the number ");
        int number =scanner.nextInt();

        System.out.println("Factors are ");
        int[] factorsOfNumber = findFactors(number);
        for (int fact : factorsOfNumber) {
            System.out.println(fact+" ");
        }

        System.out.println("The sum of factors are "+sumOfFactors(factorsOfNumber));
        System.out.println("The sum of squares of factors are " + sumOfSquares(factorsOfNumber));
        System.out.println("The product of factors are "+ productOfFactors(factorsOfNumber));
        scanner.close();
    }
	
	//a static Method to find the factors of the number and save them in an array and return the array. 
	static int[] findFactors(int number) {
        int count=0;

        //first loop to find the count and initialize the array with the count.
        for (int i=1; i<=number; i++) {
            if (number%i == 0) {
                count++;
            }
        }

        int[] factors =new int[count];
        int index=0;
        //And the second loop save the factors into the array
        for (int i=1; i<=number; i++) {
            if (number%i == 0) {
                factors[index++] = i;
            }
        }
        return factors;
    }

	//a method to find the sum of the factors using factors array
    public static int sumOfFactors(int[] factors) {
        int sum = 0;
        for (int fact: factors) {
            sum += fact;
        }
        return sum;
    }

    // a method to find the sum of square of the factors using Math.pow() method
    public static double sumOfSquares(int[] factors) {
        double sum =0;
        for (int fact : factors) {
            sum += Math.pow(fact, 2);
        }
        return sum;
    }
    
    // a method to find the product of the factors using factors array
    public static int productOfFactors(int[] factors) {
        int product = 1;
        for (int fact : factors) {
            product *= fact;
        }
        return product;
    }
}
