import java.util.*;
public class NumberGuessingGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gamePlay();
	}
	
	// this function generate a random number to user
	static int randomGuess(int high, int low) {
		Random random=new Random();
		
		// guess will be exist between low and high
		int guess =random.nextInt(high - low+1) +low;
		return guess;
	}
	
	//here we will get feedback from users
	static String userFeedback(int guess, Scanner scanner) {
		System.out.println("Is your number? - "+guess);
		
		System.out.println("Enter your feedback low/high/correct - ");
		String feedback =scanner.next();
		return feedback;
	}
	
	// here wq will updqate range according feedback
	static int[] newRange(int[] range, int guess, String feedback) {
		if(feedback.equals("high")) {
			range[1] =guess-1;
		}else if(feedback.equals("low")) {
			range[0] =guess+1;
		}
		return range;
	}
	
	static void gamePlay() {
		Scanner scanner=new Scanner(System.in);
		int high =100;
		int low =1;
		boolean correct =false;
		
		System.out.println("Hey user, think a number between 1 to 100, I wiil guess your number! ");
		
		while(!correct && low<=high) {
			int guess =randomGuess(high, low);
			String feedback =userFeedback(guess, scanner);
			if(feedback.equals("correct")) {
				System.out.println("I guessed your number.");
				correct=true;
			}else {
				int[] range = {low, high};
				range =newRange(range, guess, feedback);
				low =range[0];
				high =range[1];
			}
		}
		scanner.close();
	}
}
