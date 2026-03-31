package strings;

import java.util.*;
public class FrequencyOfCharacters{
	
	// a method to find the frequency of characters in a string using the charAt() method 
	//and return the characters and their frequencies in a 2D array.
	public static String[][] charFrequency(String str) {
        int[] freq =new int[256];
        int len =str.length();

        for (int i=0; i<len; i++) {
        	freq[str.charAt(i)]++;
        }
        int count = 0;
        for (int f : freq) {
        	if (f > 0) {
        		count++;
        	}
        }
        String[][] result = new String[count][2];
        int k = 0;
        for (int i=0; i<256; i++) {
            if (freq[i] > 0) {
                result[k][0] = Character.toString((char)i);
                result[k][1] = Integer.toString(freq[i]);
                k++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string ");
        String text = scanner.nextLine();

        String[][] freq = charFrequency(text);
        System.out.println("Character | Frequency");
        for (String[] pair :freq) {
            System.out.println( pair[0]+" ,"+ pair[1]);
        }
        scanner.close();
    }
}