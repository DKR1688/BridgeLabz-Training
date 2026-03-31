package methods;

import java.util.*;
public class BmiAndStatus {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        double[][] weightHeight = new double[10][3];
        for (int i=0; i<10; i++) {
        	System.out.println("Enter the height of person "+i+1);
            weightHeight[i][0] = scanner.nextDouble();
            
            System.out.println("Enter the weight of person "+i+1);
            weightHeight[i][1] = scanner.nextDouble();
        }
        
        calculateBMI(weightHeight);
        for (int i=0; i<10; i++) {
            System.out.println("The bmi of person is: "+weightHeight[i][2] +" And status is: "+getStatus(weightHeight[i][2]));
        }
        scanner.close();
    }
	
	//a Method to find the BMI of every person and populate the array. Use the formula BMI = weight / (height * height).
	//Note unit is kg/m^2. For this convert cm to meter
	static void calculateBMI(double[][] weightHeight) {
        for (int i=0; i<weightHeight.length; i++) {
          double heightInMeters = weightHeight[i][1]/100;
            weightHeight[i][2] = weightHeight[i][0]/(heightInMeters* heightInMeters);
        }
    }

	// a Method to determine the BMI status using the logic shown in the figure below. 
	//and return the array of all the persons BMI Status.
    static String getStatus(double bmi) {
        if (bmi<18.5)
            return "Underweight";
        else if (bmi<25)
            return "Normal";
        else if (bmi<30)
            return "Overweight";
        else
            return "Obese";
    }   
}
