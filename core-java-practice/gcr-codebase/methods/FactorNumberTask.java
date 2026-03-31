package methods;

import java.util.*;
public class FactorNumberTask{
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number= scanner.nextInt();
        int[] factor = factors(number);
        
        System.out.println("Factors are " +Arrays.toString(factor));
        System.out.println("Greatest factor is " +greatestFactor(factor));
        System.out.println("Sum is " +sum(factor));
        System.out.println("Product is " +product(factor));
        System.out.println("Product of Cubes is " +productCubes(factor));
        scanner.close();
    }
	
	//Method to find factors of a number and return them as an array. Note there are 2 for loops: one for the count and 
	//another for finding the factor and storing in the array
    static int[] factors(int num) {
        num = Math.abs(num);
        if (num==0) {
        	return new int[]{ };
        }
        int count=0;
        for (int i=1; i<= num; i++) {
        	if (num%i == 0) {
        		count++;
        	}
        }
        int[] arr = new int[count];
        int k=0;
        for (int i=1; i<=num; i++) {
        	if (num%i == 0) {
        		arr[k++] = i;
        	}
        }
        return arr;
    }
    
    //Method to find the greatest factor of a Number using the factors array
    static int greatestFactor(int[] factor) { 
    	return factor.length == 0 ? -1 : factor[factor.length - 1]; 
    }
    
    //Method to find the sum of the factors using factors array and return the sum
    static long sum(int[] factor) { 
    	long sum=0; 
    	for (int x : factor) {
    		sum += x; 
    	}
		return sum;
    }
    
    //Method to find the product of the factors using factors array and return the product
    static long product(int[] factor) {
    	long product=1; 
    	for (int x : factor) {
    		product *= x; 
    	}
    	return product;
    }
    
    //Method to find the product of the cube of the factors using the factors array. Use Math.pow()
    public static long productCubes(int[] factor) {
    	long product= 1; 
    	for (int x : factor) {
    		product *= (long)Math.pow(x, 3); 
    	}
    	return product; 
    }    
}