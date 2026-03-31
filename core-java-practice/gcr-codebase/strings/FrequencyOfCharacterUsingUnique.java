package strings;

import java.util.*;
public class FrequencyOfCharacterUsingUnique {
	
	//a method to Find unique characters in a string using the charAt() method 
	//and return them as a 1D array.  Use Nested Loops to find the unique characters in the text
	public static char[] uniqueCharacters(String str) {
        int len = str.length();
        char[] unique =new char[len];
        int k = 0;
        for (int i=0; i<len; i++) {
            char c =str.charAt(i);
            boolean isUnique = true;
            for (int j=0; j<i; j++) {
                if (str.charAt(j)==c) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
            	unique[k++] = c;
            }
        }
        char[] result = new char[k];
        System.arraycopy(unique, 0, result, 0, k);
        return result;
    }

	// a method to find the frequency of characters in a string and return the characters and their frequencies in a 2D array.
    public static String[][] charFrequencyUsingUnique(String str) {
        char[] unique = uniqueCharacters(str);
        String[][] result = new String[unique.length][2];

        //Loop through the text to find the frequency of characters in the text
        for (int i=0; i<unique.length; i++) {
            int count=0;
            for (int j=0; j<str.length(); j++) {
                if (str.charAt(j)==unique[i]) {
                	count++;
                }
            }
            result[i][0] = Character.toString(unique[i]);
            result[i][1] = Integer.toString(count);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string ");
        String text = scanner.nextLine();

        //a 2D String array to store the unique characters and their frequencies. 
        String[][] freq = charFrequencyUsingUnique(text);
        System.out.println("Character | Frequency");
        
        //Loop through the unique characters and store the characters and their frequencies
        for (String[] pair :freq) {
            System.out.println( pair[0]+","+ pair[1]);
        }
        scanner.close();
    }
}