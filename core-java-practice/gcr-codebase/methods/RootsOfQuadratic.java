package methods;

import java.util.*;
public class RootsOfQuadratic {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter a, b and c: ");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        double[] rootsOfEquation =findRoots(a, b, c);
        if (rootsOfEquation.length==0) {
            System.out.println("No real roots exist");
        } else if (rootsOfEquation.length==1) {
            System.out.println("There one root exist: " +rootsOfEquation[0]);
        } else {
            System.out.println("Two roots: " +rootsOfEquation[0] +" and " + rootsOfEquation[1]);
        }
        scanner.close();
    }
	//a Method to find find the roots of a quadratic equation and return the roots
    static double[] findRoots(double a, double b, double c) {
    	
    	// roots are computed using the following formulae
    	//delta = b2+ 4*a*c
        double delta =Math.pow(b, 2)-4*a*c;

        //If delta is negative return empty array or nothing 
        if (delta<0) {
            return new double[0];
        }
        
        //If delta is zero then there is only one root of x  
        //root of x = -b/(2*a)
        else if (delta==0) {
            double root = -b/(2*a);
            return new double[]{root};
        }
        
        //If delta is positive the find the two roots using formulae 
        //root1 of x = (-b + sqrt(delta))/(2*a) 
       	//root1 of x = (-b - sqrt(delta))/(2*a)

        else {
            double sqrtDelta = Math.sqrt(delta);
            double root1 = (-b+sqrtDelta) / (2*a);
            double root2 = (-b-sqrtDelta) / (2*a);
            return new double[]{root1, root2};
        }
    }
}
