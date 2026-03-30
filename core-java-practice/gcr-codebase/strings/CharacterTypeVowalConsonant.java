package strings;  
import java.util.*;

public class CharacterTypeVowalConsonant {
    // this method is to determine the type of a character (Vowel, Consonant, or Not a Letter)
    static String charType(char chAr) {
        if (chAr>='A' && chAr<='Z') 
            chAr = (char)(chAr+32);

        // Checking if the character is an English letter
        if (chAr>='a' && chAr<='z') {
            // If the character is a vowel
            if ("aeiou".indexOf(chAr) != -1) 
                return "Vowel";
            else 
                return "Consonant";
        }
        return "Not a Letter";
    }

    // this method to analyze each character in the given text
    // returns a 2D array where each row has {character, its type}
    static String[][] analyzeText(String text) {
        String[][] table = new String[text.length()][2];

        for (int i=0; i<text.length(); i++) {
            table[i][0] = String.valueOf(text.charAt(i));
            table[i][1] = charType(text.charAt(i));
        }
        return table;
    }

    static void displayTable(String[][] table) {
        System.out.println("Char\tType");
        
        for (int i = 0; i < table.length; i++)
            System.out.println(table[i][0] + "\t" + table[i][1]);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = scan.nextLine();

        displayTable(analyzeText(text));
        scan.close();
    }
}
