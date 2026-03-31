package ExamProctor;

public class OnlineExamReviewSystem {
	public static void main(String[] args) {
        Proctor proctor =new Proctor();

        proctor.visitQuestion(1);
        proctor.submitAnswer(1, "A");

        proctor.visitQuestion(2);
        proctor.submitAnswer(2, "B"); // Wrong

        proctor.visitQuestion(3);
        proctor.submitAnswer(3, "B");

        proctor.visitQuestion(4);
        proctor.submitAnswer(4, "D");

        int finalScore =proctor.calculateScore();
        System.out.println("Final score is-" + finalScore+"/" + 4);
    }
}
