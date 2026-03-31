package OnlineExaminationSystem;

public class Student {
	private int studentId;
	private String studentName;
	private String answers[] = new String[5];
	private int count = 0;
	
	public Student(int studentId,String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}
	
	public void submitAnswer(String answer) throws ExamTimeExpiredException{
		if(count >= 5) {
			throw new ExamTimeExpiredException("Exam time expired");
		}
		answers[count++] = answer;
	}
	
	public String[] getAnswers() {
		return answers;
	}
	
	public String getName() {
		return studentName;
	}
	
	public int getAnswerCount() {
		return count;
	}
}
