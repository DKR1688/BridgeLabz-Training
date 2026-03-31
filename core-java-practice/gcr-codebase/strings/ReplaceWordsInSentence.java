package strings;

import java.util.*;
public class ReplaceWordsInSentence {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter a sentence, given word and new word to replace ");
        String sentence =scanner.nextLine();
        String givenWord =scanner.next();
        String newWord =scanner.next();

        String newSentence = replaceWord(sentence, givenWord, newWord);
        System.out.println("Ater replacing word sentence is "+newSentence);
        scanner.close();
    }
	
	// a method to replace a given word with another word in a sentence
    public static String replaceWord(String sentence, String givenWord, String newWord) {
    	
        // Using replaceAll method we replace word and
        // "\\b" ensures we match whole words only word boundaries
        return sentence.replaceAll("\\b" + givenWord + "\\b", newWord);
    }
}
