package methods;

import java.util.*;
public class SumOfNaturalNumber {

	public static void main(String[] args) {
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the number of natural number ");
		int number=sc.nextInt();
		
		int ans =sumNaturalNumbers(number);
		if(ans==-1) {
			System.out.println("It is not a natural number.");
		}else {
			System.out.println("The sum of "+number+" natural numbers are "+ans);
		}
		sc.close();
		
	}
	
	//a Method to find the sum of n natural numbers using loop
	static int sumNaturalNumbers(int n) {
		int sum=0;
		if(n<=0) {
			return -1;
		}
		//sum of n natural numbers using loop
		for(int i=1; i<=n; i++) {
			sum += i;
		}
		return sum;
	}

}
