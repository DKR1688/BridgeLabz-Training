package methods;

import java.util.*;
public class RoundNumbersOfAthlete {
	
	public static void main(String[] args) {
	    Scanner scanner= new Scanner(System.in);
	    System.out.println("Enter number of sides of a triangle ");
	    double side1 = scanner.nextDouble();
	    double side2 = scanner.nextDouble();
	    double side3 = scanner.nextDouble();

	    //perimeter of a triangle is the addition of all sides and rounds is distance/perimeter
	    double totalRounds = calculateRounds(side1, side2, side3);
	    System.out.println("Number of rounds required to complete 5 km are "+totalRounds);
	    scanner.close();
	  }

	//a Method to compute the number of rounds user needs to do to complete 5km run
	public static double calculateRounds(double side1, double side2, double side3){
	    double perimeter = side1+side2+side3;
	    double totalDistance =5000;
	    return totalDistance/perimeter;
	  }
}
