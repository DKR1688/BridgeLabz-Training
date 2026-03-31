package methods;

import java.util.*;
public class SimpleInterest {
	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		
		System.out.println("Enter principle, rate and time ");
		double principle =scanner.nextDouble();
		double rate =scanner.nextDouble();
		double time =scanner.nextDouble();
		
		double ans=simpleInterest(principle, rate, time);
		System.out.println("The simple interest is "+ans+" for priciple "+principle+
							", rate of interest "+rate+" and time "+time);
		scanner.close();
		
	}
	
	// a method to calculate the simple interest given principle, rate and time as parameters
	static double simpleInterest(double principle, double rate, double time) {
		double simpleInterest =(principle*rate*time)/100;
		return simpleInterest;
	}

}
