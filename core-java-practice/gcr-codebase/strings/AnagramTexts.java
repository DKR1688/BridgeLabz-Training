package strings;

import java.util.Scanner;

public class AnagramTexts{

	//a method to check if two texts are anagrams
    public static boolean areAnagrams(String str1, String str2) {
        if (str1.length()!=str2.length()) return false;

        int[] freq1=new int[256];
        int[] freq2=new int[256];

        //Finding the frequency of characters in the two texts using the loop
        for (int i=0; i<str1.length(); i++) {
            freq1[str1.charAt(i)]++;
            freq2[str2.charAt(i)]++;
        }

        for (int i=0; i<256; i++) {
            if (freq1[i] != freq2[i]) {
            	return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first string ");
        String text1 = scanner.nextLine();
        System.out.println("Enter the second string ");
        String text2 = scanner.nextLine();

        if (areAnagrams(text1, text2)) {
        	System.out.println("Both strings are anagrams.");
        }
        else {
        	System.out.println("Both strings are not anagrams.");
        }
        scanner.close();
    }
}