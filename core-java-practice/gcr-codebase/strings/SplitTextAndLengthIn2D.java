package strings;
// a program to split the text into words and return the words along with their lengths in a 2D array
import java.util.Scanner;
public class SplitTextAndLengthIn2D {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter string to split:");
        String inputLine = scanner.nextLine();

        String[] words = extractWords(inputLine);
        String[][] wordLengthTable = buildWordLengthMatrix(words);

        System.out.println("Word and Length");
        for (int i=0; i<wordLengthTable.length; i++) {
            String word =wordLengthTable[i][0];
            
            // Converting length from String back to int before displaying.
            int length =Integer.parseInt(wordLengthTable[i][1]);
            System.out.println(word + " And " + length);
        }
        scanner.close();
	}

    // it returns the number of characters in the given String without using length()
    public static int getStringLength(String text) {
        int length=0;
        try {
            while (true) {
                text.charAt(length); //it will throw exception when index goes out of bounds
                length++;
            }
        } catch (IndexOutOfBoundsException e) {
            // this exception used only to detect end of string
        }
        return length;
    }

    // it breaks the input text into words using spaces, without using the split() method
    public static String[] extractWords(String sentence) {
        int textLength =getStringLength(sentence);
        String[] tempWords =new String[textLength];
        int totalWords = 0;

        String currentWord ="";
        for (int i=0; i<textLength; i++) {
            char currentChar=sentence.charAt(i);

            if (currentChar == ' ') {
                if (!currentWord.equals("")) {
                    tempWords[totalWords++] =currentWord;
                    currentWord = "";
                }
            } else {
                currentWord = currentWord+currentChar;
            }
        }

        // Add the last word if there is one in the string
        if (!currentWord.equals("")) {
            tempWords[totalWords++] =currentWord;
        }

        // Copy only the filled entries into the final words array.
        String[] words = new String[totalWords];
        for (int i = 0; i < totalWords; i++) {
            words[i] = tempWords[i];
        }
        return words;
    }

    // Building a 2D String array where each row contains a word and its length as a String.
    public static String[][] buildWordLengthMatrix(String[] words) {
        int numberOfWords =words.length;
        String[][] wordLengthMatrix =new String[numberOfWords][2];

        for (int i=0; i<numberOfWords; i++) {
            wordLengthMatrix[i][0] = words[i];
            int wordLength = getStringLength(words[i]);
            wordLengthMatrix[i][1] = String.valueOf(wordLength); // Store length as String.
        }
        return wordLengthMatrix;

    }
}
