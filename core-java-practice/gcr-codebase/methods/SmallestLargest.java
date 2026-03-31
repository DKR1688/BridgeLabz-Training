package methods;

import java.util.*;
public class SmallestLargest {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter 3 numbers ");
		int number1=sc.nextInt();
		int number2=sc.nextInt();
		int number3=sc.nextInt();
		
		int[] ans =findSmallestAndLargest(number1, number2, number3);
		
		System.out.println("The largest number is "+ ans[1]);
		System.out.println("The smallest number is "+ ans[0]);
		sc.close();

	}
	
	// a single method to find the smallest and largest of the three numbers
	static int[] findSmallestAndLargest(int number1, int number2, int number3) {
		int smallest;
		int largest;

        smallest = (number1<number2) ? (number1<number3 ? number1 : number3) : (number2<number3 ? number2 : number3);

        largest = (number1>number2) ? (number1>number3 ? number1 : number3) : (number2>number3 ? number2 : number3);

        // here we are returning both in an array
        return new int[]{smallest, largest};

	}

}
