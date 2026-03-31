import java.util.*;
public class EduQuizG {

    //method to calculate score
    static int calculateScore(String[] correct, String[] student) {
        int score= 0;
        for (int i=0; i<correct.length; i++) {
            if (student[i].equalsIgnoreCase(correct[i])) {
                score++;
                System.out.println("Question " +(i+1)+ "is Correct.");
            } else {
                System.out.println("Question " +(i+1)+ "is Incorrect.");
            }
        }
        return score;
    }

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        //question to students
        String[] questions = {"What is the capital of India?", 
        					  "Which is the largest ocean?", 
        					  "What is 2 + 2?"};

        //choises to student to answer the question
        String[][] options = {{"A) Delhi", "B) Mumbai", "C) Kolkata", "D) Chennai"},
        					  {"A) Atlantic", "B) Indian", "C) Pacific", "D) Arctic"}, 
        					  {"A) 3", "B) 4", "C) 5", "D) 6"} };

        String[] correctAnswers= {"A", "C", "B"};
        String[] studentAnswers= new String[questions.length];

        //this is to ask questions
        for (int i=0; i<questions.length; i++) {
            System.out.println("\nQuestion " +(i+1)+ " is- " +questions[i]);
            
            for (String opt : options[i]) {
                System.out.println(opt);
            }
            
            System.out.print("Enter your answer A or B or C or D- ");
            studentAnswers[i] = scanner.nextLine();
        }

        int score =calculateScore(correctAnswers, studentAnswers);
        System.out.println("\nFinal score is- " +score+ " out of "+correctAnswers.length);

        double percentage = (score*100.0) / correctAnswers.length;
        System.out.printf("Percentage: %.2f%%\n", percentage);

        if (percentage>=50) {
            System.out.println("Student is PASS.");
        } else {
            System.out.println("Student is FAIL.");
        }
        scanner.close();
    }
}