package strings;
import java.util.*;

public class PercentageGrade {
	
	//a method to generate random 2-digit scores for Physics, Chemistry and Math (PCM) 
	//for the students and return the scores. This method returns a 2D array with PCM scores for all students
    static int[][] generateScores(int n) {
        int[][] scores = new int[n][3];
        for (int i=0; i<n; i++) {
            scores[i][0]= 30 + (int)(Math.random() * 71); // Physics 30-100
            scores[i][1]= 30 + (int)(Math.random() * 71); // Chemistry
            scores[i][2]= 30 + (int)(Math.random() * 71); // Maths
        }
        return scores;
    }

    //a Method to calculate the total, average, and percentages for each student and 
    //return a 2D array with the corresponding values. Please ensure to round off the values to 2 Digits using Math.round() method
    static double[][] calculateStats(int[][] scores) {
        int n =scores.length;
        double[][] stats = new double[n][2];

        for (int i=0; i<n; i++) {
            int total = scores[i][0] + scores[i][1] + scores[i][2];
            double average = Math.round((total / 3.0) * 100.0) / 100.0;
            stats[i][0] = total;
            stats[i][1] = average;
        }
        return stats;
    }

    //a Method to calculate the grade based on the percentage as 
    //shown in the ref table and return a 2D array of students' grade
    static String[][] calculateGrade(double[][] stats) {
        int n = stats.length;
        String[][] result = new String[n][2]; // grade, remark

        for (int i=0; i<n; i++) {
            double avg = stats[i][1];
            if (avg >= 80) {
                result[i][0] = "A";
                result[i][1] = "(Level 4, above agency-normalized standards)";
            } else if (avg >= 70) {
                result[i][0] = "B";
                result[i][1] = "(Level 3, at agency-normalized standards)";
            } else if (avg >= 60) {
                result[i][0] = "C";
                result[i][1] = "(Level 2, below, but approaching agency-normalized standards)";
            } else if (avg >= 50) {
                result[i][0] = "D";
                result[i][1] = "(Level 1, well below agency-normalized standards)";
            } else if (avg >= 40) {
                result[i][0] = "E";
                result[i][1] = "(Level 1-, too below agency-normalized standards)";
            } else {
                result[i][0] = "R";
                result[i][1] = "(Remedial Standards)";
            }
        }
        return result;
    }

    //a Method to display the scorecard of all students with their scores, total, average, percentage, and grade in a tabular format. 
    static void displayScorecard(int[][] scores, double[][] stats, String[][] grades) {
        System.out.println("S.No\tPhysics\tChemistry\tMaths\tTotal\tAverage\tGrade\tRemarks");
        for (int i = 0; i < scores.length; i++) {
            System.out.println((i+1) + "\t" + scores[i][0] + "\t" + scores[i][1] + "\t\t" + scores[i][2] + 
                              "\t" + stats[i][0] + "\t" + stats[i][1] + "\t" + grades[i][0] + "\t" + grades[i][1]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();

        int[][] scores = generateScores(n);
        double[][] stats = calculateStats(scores);
        String[][] grades = calculateGrade(stats);

        displayScorecard(scores, stats, grades);

        scanner.close(); 
    }
}