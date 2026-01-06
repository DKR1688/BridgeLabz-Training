import java.util.*;
//custom exception class
class InvalidQuizSubmissionException extends Exception {
    public InvalidQuizSubmissionException(String messege) {
        super(messege);
    }
}

class QuizPlateformWithException {
	public static void main(String[] args) {
        String[] correct = {"A","D","C","D","A","True","Paris","OOPS","Java","TCP"};

        String[] user1 = {"A","c","B","D","b","true","Paris","oop","java","tcp"}; // all correct
        String[] user2 = {"B","C","B","A","B","False","London","OOP","Python","UDP"}; //correct or incorrect
        String[] user3 = {"A","C","B"}; // invalid length

        List<Integer> scores = new ArrayList<>();
        //user1
        try {
            int s1 = calculateScore(correct, user1);
            scores.add(s1);
            System.out.println("User1 score is- " +s1 + "/" + correct.length + "\n Grade is- " + grade(s1, correct.length));
        } catch (Exception e) { System.out.println("User1 Error: " + e.getMessage()); }

        // User2
        try {
            int s2 =calculateScore(correct, user2);
            scores.add(s2);
            System.out.println("User2 score is- " +s2 + "/" + correct.length + "\n Grade is- " +grade(s2, correct.length));
        } catch (Exception e) { System.out.println("User2 Error: " + e.getMessage()); }

        // User3
        try {
            int s3 =calculateScore(correct, user3);
            scores.add(s3);
            System.out.println("User3 score is- " +s3 + "/" +correct.length + "\n Grade is- " + grade(s3, correct.length));
        } catch (Exception e) { System.out.println("User3 Error: " + e.getMessage()); }

        //displaying all valid scores
        System.out.println("All valid scores are- " +scores);
    }

    //validate input arrays
    static void validate(String[] correct, String[] user) throws InvalidQuizSubmissionException {
        if (correct==null || user==null || correct.length != user.length) {
            throw new InvalidQuizSubmissionException("Invalid submission- length mismatch or null arrays.");
        }
    }

    //this method is to calculate score
    static int calculateScore(String[] correct, String[] user) throws InvalidQuizSubmissionException {
        validate(correct, user);
        int score=0;
        for (int i=0; i<correct.length; i++) {
            if (correct[i].trim().equalsIgnoreCase(user[i].trim())) {
                score++;
            }
        }
        return score;
    }

    //this method is to assign grade
    static String grade(int score, int total) {
        double percent = (score * 100.0)/total;
        if (percent >=90) {
        	return "A";
        }
        if (percent >=75) {
        	return "B";
        }
        if (percent >=60) {
        	return "C";
        }
        if (percent >=40) {
        	return "D";
        }
        return "F";
    }
}