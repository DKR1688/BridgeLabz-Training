package strings;

import java.util.*;
public class LongestWord {
	public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the sentence:");
        String sentence = scanner.nextLine();

        String longestWord = findLongestWord(sentence);
        System.out.println("Longest word in the sentence is "+longestWord);
        scanner.close();
    }
	
	// a method to find the longest word
    public static String findLongestWord(String sentence) {
    	
        // Splitting the sentence into words using space as delimiter
        String[] words = sentence.split(" ");

        String longestWord = "";
        int maxLength = 0;
        for (String word : words) {
        	
            // Removing punctuation
            word = word.replaceAll("[^a-zA-Z]", "");
            if (word.length()>maxLength) {
                maxLength=word.length();
                longestWord=word;
            }
        }
        return longestWord;
    }
}
