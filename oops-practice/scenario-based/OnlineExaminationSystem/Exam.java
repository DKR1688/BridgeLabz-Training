package OnlineExaminationSystem;

public class Exam {
	private Question[] questions = new Question[5];
    private int count = 0;

    public void addQuestion(Question question) {
        questions[count++] = question;
    }

    public void conductExam(Student student) throws ExamTimeExpiredException {

        for (int i = 0; i < count; i++) {
            student.submitAnswer("4");
        }
    }

    public int evaluateResult(Student student,EvaluationStrategy strategy) {
    	return strategy.evaluate(questions,student.getAnswers(),student.getAnswerCount());
    }
}
