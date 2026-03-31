package strings;

import java.util.*;
public class FrequencyOfCharacterUsingNestedLoops {
	
	//a method to find the frequency of characters in a string and return the characters and their frequencies in a 1D array
    public static String[][] charFrequencyNested(String str) {
        char[] chars =str.toCharArray();
        int len =chars.length;
        int[] freq=new int[len];

        //nested loops to Find the frequency of each character in the text and store the result in a frequency array.
        for (int i=0; i<len; i++) {
            if (chars[i]=='0') {
            	continue;
            }
            freq[i]=1;
            for (int j=i+1; j<len; j++) {
                if (chars[i]== chars[j]) {
                    freq[i]++;
                    chars[j]= '0';
                }
            }
        }

        int count = 0;
        for (int f : freq) {
        	if (f != 0) {
        		count++;
        	}
        }
        String[][] result = new String[count][2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            if (freq[i] != 0) {
                result[k][0] = Character.toString(chars[i]);
                result[k][1] = Integer.toString(freq[i]);
                k++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string ");
        String text =scanner.nextLine();

        String[][] freq =charFrequencyNested(text);
        System.out.println("Character | Frequency");
        for (String[] pair :freq) {
            System.out.println(pair[0]+", "+ pair[1]);
        }
        scanner.close();
    }
}