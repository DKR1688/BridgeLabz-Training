package OnlineExaminationSystem;

public class DescriptiveEvaluation implements EvaluationStrategy{
	int score = 0;
	
	@Override
	public int evaluate(Question questions[],String answers[],int count) {
		for(int i = 0;i < count;i++) {
			if(questions[i].getcorrectAnswer().equalsIgnoreCase(answers[i])) {
				score += questions[i].getMarks()/2;
			}
		}
		return score;
	}
}
