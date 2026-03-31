package ExamProctor;
import java.util.*;
class Proctor {
    //each student’s question navigation is recorded using a Stack last visited question
    Stack<Integer> navigationStack =new Stack<>();

    //Answers are stored in a HashMap: questionID → answer
    HashMap<Integer, String> answersMap = new HashMap<>();

    //A function auto-calculates the score once the student submits
    HashMap<Integer, String> correctAnswers = new HashMap<>();

    //constructor to load correct answers
    Proctor() {
        correctAnswers.put(1, "A");
        correctAnswers.put(2, "C");
        correctAnswers.put(3, "B");
        correctAnswers.put(4, "D");
    }
    
    //Track navigation with stack.
    public void visitQuestion(int questionID) {
        navigationStack.push(questionID);
        System.out.println("Visited question is- " +questionID);
    }

    //store answers in a map.
    public void submitAnswer(int questionID, String answer) {
        answersMap.put(questionID, answer);
        System.out.println("Answer submitted for Q" + questionID + "- " + answer);
    }
    
    //Evaluate using functions for scoring logic.
    public int calculateScore() {
        int score = 0;
        for (Map.Entry<Integer, String> entry :answersMap.entrySet()) {
            int qID = entry.getKey();
            String givenAnswer = entry.getValue();
            String correctAnswer = correctAnswers.get(qID);

            if (givenAnswer.equals(correctAnswer)) {
                score++;
            }
        }
        return score;
    }
}


