package methods;

import java.util.*;
public class NumberCheckerFifth{
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number ");
        int number= scanner.nextInt();
        
        System.out.println("Perfect number or not - " +isPerfect(number));
        System.out.println("Abundant number or not - " +isAbundant(number));
        System.out.println("Deficient number or not - " +isDeficient(number));
        System.out.println("Strong number or not - " +isStrong(number));
        scanner.close();
    }
	
	
    public static int[] divisors(int num) {
        if (num<=1) {
        	return new int[]{ };
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        
        int r= (int)Math.sqrt(num);
        for (int i=2; i<=r; i++) {
            if (num%i == 0) {
                list.add(i);
                if (i != num/i) {
                	list.add(num/i);
                }
            }
        }
        int[] arr = new int[list.size()];
        for (int i=0; i<list.size(); i++) {
        	arr[i] = list.get(i);
        }
        return arr;
    }
	
	//Method to Check if a number is a perfect number. 
	//Perfect numbers are positive integers that are equal to the sum of their proper divisors
    public static boolean isPerfect(int num) {
        int n=0; 
        for (int i:divisors(num)) {
        	n += i; 
        }
        return n== num;
    }
    
    //Method to find the number is an abundant number. 
    //A number is called an abundant number if the sum of its proper divisors is greater than the number itself
    public static boolean isAbundant(int num) {
        int n = 0; 
        for (int i: divisors(num)) {
        	n +=i; 
        }
        return n> n;
    }
    
    //Method to find the number is a deficient number. 
    //A number is called a deficient number if the sum of its proper divisors is less than the number itself
    public static boolean isDeficient(int num) {
        int n= 0; 
        for (int i :divisors(num)) {
        	n += i; 
        }
        return n <num;
    }
    
    //Method to Check if a number is a strong number. 
    //A number is called a strong number if the sum of the factorial of its digits is equal to the number itself
    static long fact(int num) { 
    	long fact= 1; 
    	for (int i=2; i<=num; i++) {
    		fact*= i; 
    	}
    	return fact; 
    }
    public static boolean isStrong(int num) {
        int n = Math.abs(num);
        int m= 0;
        while (n> 0) { 
        	m += fact(n % 10); 
        	n/=10; 
        }
        return m== Math.abs(n);
    }
    
}