import java.util.*;
//custom Exception class
class InvalidScoreException extends Exception {
    public InvalidScoreException(String messege) {
        super(messege);
    }
}

public class StudentScoreAnalyzer {

    //this method is to check scores
    public static void validateScores(int[] scores) throws InvalidScoreException {
        for (int score : scores) {
            if (score<0 || score>100) {
                throw new InvalidScoreException("Invalid score is- " +score);
            }
        }
    }

    //this method is to calculate average
    public static double calculateAverage(int[] scores) throws InvalidScoreException {
        validateScores(scores);
        int sum=0;
        for (int score : scores) {
        	sum +=score;
        }
        return (double) sum/scores.length;
    }

    //this method is to find highest
    public static int findMax(int[] scores) throws InvalidScoreException {
        validateScores(scores);
        int max = scores[0];
        for (int score : scores) {
        	if (score>max) {
        		max =score;
        	}
        }
        return max;
    }

    //this method is to find lowest
    public static int findMin(int[] scores) throws InvalidScoreException {
        validateScores(scores);
        int min = scores[0];
        for (int score: scores) {
        	if (score< min) {
        		min =score;
        	}
        }
        return min;
    }

    public static void main(String[] args) {
    	Scanner scanner =new Scanner(System.in);
    	System.out.println("Enter number of students- ");
    	int num=scanner.nextInt();
    	int[] scores =new int[num];
    	
    	System.out.println("Enter scores of students- ");
    	for(int i=0; i<scores.length; i++) {
    		scores[i] =scanner.nextInt();
    	}

        try {
            System.out.println("Average score is- " + calculateAverage(scores));
            System.out.println("Highest score is- " + findMax(scores));
            System.out.println("Lowest score is- " + findMin(scores));
        } catch (InvalidScoreException e) {
            System.out.println("Error is- " + e.getMessage());
        }
    }
}