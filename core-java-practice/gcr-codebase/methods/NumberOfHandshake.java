package methods;

import java.util.*;
public class NumberOfHandshake {
	public static void main(String[] args) {
		System.out.println("Enter num of students ");
		Scanner scanner =new Scanner(System.in);
		int numberOfStudent =scanner.nextInt();
		
		int ans =getHandshake(numberOfStudent);
		System.out.println("The maximum number of handshakes are "+ans);
		scanner.close();
	}
	
	//a method to use the combination formulae to calculate the number of handshakes
	static int getHandshake(int n) {
		int combinations =(n*(n-1))/2;
		return combinations;
	}

}
