package methods;

import java.util.*;
public class PositiveNegativeZeroNumber {
	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		System.out.println("Enter the number ");
		int number =scanner.nextInt();
		
		int ans =checkNumber(number);
		if(ans==0) {
			System.out.print("The number is Zero");
		}else if(ans==1) {
			System.out.print("The number is positive");
		}else {
			System.out.print("The number is Negative");
		}
		scanner.close();
	}
	static int checkNumber(int n) {
		if(n==0) {
			return 0;
		}else if(n>0) {
			return 1;
		}
		return -1;
	}

}
