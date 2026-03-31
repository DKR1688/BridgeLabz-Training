import java.util.*;
class ParagraphAnalyzer {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter the text:");
        String text = scanner.nextLine();
        
        if (text.trim().isEmpty()) {
            System.out.println("Empty string.");
        }
        
        countWords(text);
        displayLongestWord(text);
        replace(text, scanner);
        //Choice(text, scanner);
    }
	
    //method to count words and display the words length in the string
    static void countWords(String str) {
        String[] word = str.split(" ");
        System.out.println("The number of words in the paragraph is- "+word.length);
    }

    //method to find and display the longest word{
    static void displayLongestWord(String str) {
        String[] word = str.split(" ");
        String longestWord = "";
        
        for (int i=0; i<word.length; i++) {
            if (longestWord.length() < word[i].length()){
                longestWord = word[i];
            }
        }
        System.out.println("The Longest word in the string is- " +longestWord);
    }

    //method to replace a specific word with another word and display the updated para:
    static void replace(String text, Scanner sc) {   //
        //taking the word want to replace.
        System.out.println("Enter the word you want to replace is- ");
        String oldWord = sc.nextLine();
        System.out.println("Enter the word you want to replace the word with- ");
        String newWord = sc.nextLine();

        String[] words = text.split(" ");
        for (int i=0; i<words.length; i++) {
            if (words[i].equals(oldWord)){
                words[i] = newWord;
            }
        }
        
        //convert string array to string and join them by " "
        String str = String.join(" ", words);
        System.out.println(str);
    }
}