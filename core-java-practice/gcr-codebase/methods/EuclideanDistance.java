package methods;

import java.util.*;
public class EuclideanDistance {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter value of points ");
        double x1 = scanner.nextDouble(), y1 = scanner.nextDouble();
        double x2 = scanner.nextDouble(), y2 = scanner.nextDouble();
        
        System.out.println("Distance is " +distance(x1,y1,x2,y2));
        if (x2 == x1) {
        	System.out.println("Vertical line is  x = " +x1);
        }
        else {
        	//an array having slope m and y-intercept b 
            double[] eq = lineEquation(x1,y1,x2,y2);
            System.out.println("y = " +eq[0]+ " * x + " +eq[1]);
        }
        scanner.close();
    }
	
	//Method to find the Euclidean distance between two points and return the distance
    public static double distance(double x1,double y1,double x2,double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) +Math.pow(y2 - y1, 2));
    }
    
    //a Method to find the equation of a line given two points and return the equation 
    //which includes the slope and the y-intercept
    public static double[] lineEquation(double x1,double y1,double x2,double y2) {
        double m = (y2 - y1)/(x2 - x1);
        double b = y1 -m*x1;
        return new double[]{m, b};
    }
}