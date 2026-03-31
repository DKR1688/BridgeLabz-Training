package methods;

import java.util.*;
public class SumOfNaturalsUsingRecursive {
	public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the number ");
        int number = scanner.nextInt();

        if (number<=0) {
            System.out.println("It is not a natural number.");
        } else {
            int sum1 = recursiveSum(number);
            int sum2 = formulaeSum(number);
            
            //Compare the two results and print the result
            if(sum1==sum2) {
            	System.out.println("Both recursive and formula sum are equal to "+sum1);
            }else {
                System.out.println("Recursive Sum: " +sum1);
                System.out.println("Formula Sum: " +sum2);
            }
        }
        scanner.close();
    }
	
	//a Method to find the sum of n natural numbers using recursion
	static int recursiveSum(int n) {
	        if (n==0) {
	        	return 0;
	        }
	        return n + recursiveSum(n - 1);
	    }

	//a Method to find the sum of n natural numbers using the formulae n*(n+1)/2 
	static int formulaeSum(int n) {
	        return n * (n + 1) / 2;
	    }
}
