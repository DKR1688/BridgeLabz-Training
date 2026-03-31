package OnlineExaminationSystem;

public class OnlineExamSystem {

	public static void main(String[] args) {
		
		Exam exam1 = new Exam();

        exam1.addQuestion(new Question(1, "2+2?", "4", 2));
        exam1.addQuestion(new Question(2, "Capital of India?", "Delhi", 2));

        Student student = new Student(101, "Deepak");

        try {
            exam1.conductExam(student);
        } catch (ExamTimeExpiredException e) {
            System.out.println(e.getMessage());
        }

        EvaluationStrategy strategy = new ObjectiveEvaluation();

        int result = exam1.evaluateResult(student, strategy);

        System.out.println("Student name is- " + student.getName());
        System.out.println("Score is- " + result);

	}

}
