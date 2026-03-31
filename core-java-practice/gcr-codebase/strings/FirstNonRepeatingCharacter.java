package strings;

import java.util.*;
public class FirstNonRepeatingCharacter{
	
	// a Method to find the first non-repeating character in a string using 
	//the charAt() method and return the character
    public static char firstNonRepeating(String str) {
        int[] freq =new int[256];
        int len =str.length();

        for (int i=0; i<len; i++) {
        	freq[str.charAt(i)]++;
        }
        for (int i=0; i<len; i++) {
            if (freq[str.charAt(i)] == 1) {
            	return str.charAt(i);
            }
        }
        return '\0';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string:");
        String text = scanner.nextLine();

        char result = firstNonRepeating(text);
        if (result != '\0') System.out.println("First non-repeating character is " + result);
        else System.out.println("here no non-repeating character found.");
        scanner.close();
    }
}