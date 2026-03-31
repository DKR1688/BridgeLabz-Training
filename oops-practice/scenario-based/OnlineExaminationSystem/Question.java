package OnlineExaminationSystem;

public class Question {
	private int questionId;
	private String question;
	private String correctAnswer;
	private int marks;
	
	public Question(int questionId,String question,String correctAnswer,int marks) {
		this.questionId = questionId;
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.marks = marks;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public int getMarks() {
		return marks;
	}
	
	public String getcorrectAnswer() {
		return correctAnswer;
	}
}
