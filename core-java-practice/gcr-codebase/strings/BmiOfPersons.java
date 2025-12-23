package strings;

import java.util.*;
public class BmiOfPersons{
  
	// a Method to find the BMI and status of every person given the person's height and 
	//weight and return the 2D String array. Use the formula BMI = weight / (height * height). 
	//Note unit is kg/m^2. For this convert cm to meter
    public static String[] calculateBMI(double weightKg, double heightCm) {
        double heightM = heightCm/100.0;
        double bmi = weightKg / (heightM*heightM);
        String status= "";

        if (bmi<18.5) {
        	status = "Underweight";
        }
        else if (bmi<25) {
        	status = "Normal";
        }
        else if (bmi<30) {
        	status = "Overweight";
        }
        else {
        	status = "Obese";
        }

        return new String[]{String.format("%.2f", weightKg), String.format("%.2f", heightCm),
                            String.format("%.2f", bmi), status};
    }

    // a Method that takes the 2D array of height and weight as parameters. Calls the user-defined method to 
    //compute the BMI and the BMI Status and stores in a 2D String array of height, weight, BMI, and status.
    public static String[][] processTeam(double[][] data) {
        String[][] result = new String[data.length][4];
        for (int i=0; i<data.length; i++) {
            result[i] = calculateBMI(data[i][0], data[i][1]);
        }
        return result;
    }

    //a method to display the 2D string array in a tabular format of Person's Height, Weight, BMI, and the Status
    public static void displayBMI(String[][] bmiData) {
        System.out.printf("%-10s %-10s %-10s %-15s\n", "Weight(kg)", "Height(cm)", "BMI", "Status");
        for (String[] person :bmiData) {
            System.out.printf("%-10s %-10s %-10s %-15s\n", person[0], person[1], person[2], person[3]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[][] teamData = new double[10][2];

        for (int i=0; i<10; i++) {
            System.out.println("Enter weight (kg) of person " +(i+1)+ ":");
            teamData[i][0] = scanner.nextDouble();
            System.out.println("Enter height (cm) of person " +(i+1)+ ":");
            teamData[i][1] = scanner.nextDouble();
        }

        String[][] bmiResults = processTeam(teamData);
        displayBMI(bmiResults);
        scanner.close();
      }
}