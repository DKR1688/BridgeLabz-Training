package OnlineExaminationSystem;

public interface EvaluationStrategy {
	public int evaluate(Question questions[],String answers[],int answerCount);
}
