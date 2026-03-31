import java.util.*;
public class StudentTestScore {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the number of students is- ");
        int number= 0;
        
        //Handle invalid input like negative scores or non-numeric input.
        while (true) {
            try {
                number= Integer.parseInt(scanner.nextLine());
                if (number<=0) {
                    System.out.println("Number of students can not be negative.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value- ");
            }
        }
        System.out.println();

        double[] scores = new double[number];
        for (int i=0; i<number; i++) {
            while (true) {
                System.out.print("Enter score of student "+(i+1)+"- ");
                try {
                    double score =Double.parseDouble(scanner.nextLine());
                    if (score<0) {
                        System.out.println("Score cannot be negative.");
                    } else {
                        scores[i] =score;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a numeric value- ");
                }
            }
        }

        //Calculating average, highest and lowest
        double sum=0;
        for(int i=0; i<scores.length; i++) {
        	sum+=scores[i];
        }
        double average = sum/number;

        double highest =scores[0];
        double lowest =scores[0];
        for (int i=0; i<scores.length; i++) {
            if (scores[i] > highest) {
            	highest = scores[i];
            }
            if (scores[i] < lowest) {
            	lowest = scores[i];
            }
        }

        System.out.println("Average score is- " +average);
        System.out.println("Highest score is- " +highest);
        System.out.println("Lowest score is- " +lowest);
        System.out.println();

        System.out.println("Scores above average is- ");
        boolean foundAbove = false;
        for (int i=0; i<scores.length; i++) {
            if (scores[i] > average) {
                System.out.println(scores[i]);
                foundAbove =true;
            }
        }
        if (!foundAbove) {
            System.out.println("None");
        }
        scanner.close();
    }
}