// An organization took up an exercise to find the Body Mass Index (BMI) 
// of all the persons in the team. For this create a program to find the 
// BMI and display the height, weight, BMI and status of each individual
import java.util.Scanner;
public class BmiOfPerson {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of persons ");
        int numberOfPerson = scanner.nextInt();

        double[] bmi = new double[numberOfPerson];
        double[] height = new double[numberOfPerson];
        double[] weight = new double[numberOfPerson];
        String[] status = new String[numberOfPerson];

        // here we are calculating height and weight for each person
        for (int i=0; i<numberOfPerson; i++) {
            System.out.println("\nPerson "+(i+1)+":");
            System.out.print("Enter height and weight of person: ");
            height[i] =scanner.nextDouble();
            weight[i] =scanner.nextDouble();

            bmi[i] = weight[i]/(height[i]*height[i]);

            // status of persons
            if (bmi[i]<=18.4) {
                status[i] = "Underweight";
            } else if (bmi[i]<24.9) {
                status[i] = "Normal weight";
            } else if (bmi[i]<39.9) {
                status[i] = "Overweight";
            } else {
                status[i] = "Obese";
            }
        }

        // printing the height, weight bmi and status of persons
        System.out.println("\n-- BMI Report --");
        for (int i=0; i<numberOfPerson; i++) {
            System.out.println("Person "+(i+1)+ ":");
            System.out.println("Height is " + height[i]);
            System.out.println("Weight is " + weight[i]);
            System.out.println("BMI is " + bmi[i]);
            System.out.println("Status is " + status[i]);
        }
        scanner.close();
    }
}