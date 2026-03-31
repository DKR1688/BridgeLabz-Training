package methods;

import java.util.*;
public class TrignometricAndMathFunction {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the agle of triangle ");
        double angleInDegree = scanner.nextDouble();

        double[] ans= calculateTrigonometricFunctions(angleInDegree);
        System.out.println("The Sin is " +ans[0]);
        System.out.println("The Cos is " +ans[1]);
        System.out.println("The Tan is " +ans[2]);
        scanner.close();
    }
	
	//Method to calculate various trigonometric functions, Firstly convert to radians and 
	//then use Math function to find sine, cosine and tangent.
	public static double[] calculateTrigonometricFunctions(double angle) {
        double radians= Math.toRadians(angle);
        double sin= Math.sin(radians);
        double cos= Math.cos(radians);
        double tan= Math.tan(radians);

        return new double[]{sin, cos, tan};
    }

}
