package methods;

import java.util.*;
public class AverageAndPercentageScore{
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int number = scanner.nextInt();

        int[][] scores = generateScores(number);
        double[][] ans = calculateTotals(scores);
        displayScorecard(scores, ans);

        scanner.close();
    }

    //a method to generate random 2-digit scores for Physics, Chemistry, and Math (PCM) for the students and return the scores. 
	//This method returns a 2D array with PCM scores for all students
    public static int[][] generateScores(int students) {
        int[][] scores = new int[students][3];
        for (int i=0; i<students; i++) {
            for (int j=0; j<3; j++) {
                scores[i][j] = 10 + (int)(Math.random() * 91);
            }
        }
        return scores;
    }

    //a Method to calculate the total, average, and percentages for each student and return a 2D array with the corresponding values. 
    //Please ensure to round off the values to 2 Digits using the Math.round() method. 
    public static double[][] calculateTotals(int[][] scores) {
        int students = scores.length;
        double[][] results = new double[students][3];

        for (int i=0; i<students; i++) {
            int total = scores[i][0] + scores[i][1] + scores[i][2];
            double average = total / 3.0;
            double percentage = (total / 300.0) * 100;

            results[i][0] = Math.round(total * 100.0) / 100.0;
            results[i][1] = Math.round(average * 100.0) / 100.0;
            results[i][2] = Math.round(percentage * 100.0) / 100.0;
        }
        return results;
    }

    //a Method to display the scorecard of all students with their scores, total, average, 
    //and percentage in a tabular format using "\t".
    public static String calculateGrade(double average) {
        if (average >= 80) {
        	return "A";
        }else if (average >= 70) {
        	return "B";
        }else if (average >= 60) {
        	return "C";
        }else if (average >= 50) {
        	return "D";
        }else if (average >= 40) {
        	return "E";
        }else {
        	return "R";
        }
    }

    public static String calculateRemark(double average) {
        if (average >= 80) {
        	return "(Level 4, above agency-normalized standards)";
        }else if (average >= 70) {
        	return "(Level 3, at agency-normalized standards)";
        }else if (average >= 60) {
        	return "(Level 2, below, but approaching agency-normalized standards)";
        }else if (average >= 50) {
        	return "(Level 1, well below agency-normalized standards)";
        }else if (average >= 40) {
        	return "(Level 1-, too below agency-normalized standards)";
        }else {
        	return "(Remedial Standards)";
        }
    }

    public static void displayScorecard(int[][] scores, double[][] results) {
        System.out.println("Student\tPhysics\tChemistry\tMaths\tTotal\tAverage\tPercentage\tGrade\tRemark");
        for (int i=0; i<scores.length; i++) {
            System.out.print((i + 1) + "\t");
            for (int j=0; j<3; j++) {
                System.out.print(scores[i][j] + "\t");
            }
            double avg = results[i][1];
            System.out.print(results[i][0] + "\t" + avg + "\t" + results[i][2] + "\t");
            System.out.print(calculateGrade(avg) + "\t" + calculateRemark(avg));
            System.out.println();
        }
    }
}