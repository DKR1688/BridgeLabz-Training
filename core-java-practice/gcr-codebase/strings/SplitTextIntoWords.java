// a program to split the text into words, compare 
//the result with the split() method and display the result 

package strings;

import java.util.*;
public class SplitTextIntoWords {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the string to split: ");
		String string=scanner.nextLine();
		
		// split using built in method
		String[] builtInMethod = string.split(" ");
		
		// split using user defined method
		String[] userDefined = userSplit(string);
		
		// here we will compare both methods
		boolean isEqual=compare(builtInMethod, userDefined);
		
		System.out.println("Split by built in method are: ");
        for (String word: builtInMethod) {
            System.out.println(word);
        }

        System.out.println("Split by user defined method are: ");
        for (String word :userDefined) {
            System.out.println(word);
        }
        System.out.println("After comparison ans is: " + isEqual);
        scanner.close();
	}
	
	// here we find length of string by using try catch to handle out of bound index error without length()
	public static int lengthOfString(String str) {
		int count=0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (Exception e) {
        }
        return count;
	}
	
	// here splitting string into words by user defined method
	public static String[] userSplit(String str) {
		int length = lengthOfString(str);
		
		// counting words in string
		int count = 1;
		for(int i=0; i<length; i++) {
			if(str.charAt(i)==' ') {
				count++;
			}
		}
		
		// storing index of space to seprate
		int[] spaces = new int[count-1];
		int index =0;
		for(int i=0; i<length; i++) {
			if(str.charAt(i)==' ') {
				spaces[index++]=i;
			}
		}
		
		// extracting words using space indexes
		String[] words = new String[count];
		int start=0;
		index=0;
		for(int i=0; i<length; i++) {
			if(str.charAt(i)==' ') {
			words[index++] = str.substring(start, i);
			start = i+1;
			}
		}
		
		// this is the last word of string
		words[index]=str.substring(start, length);
		
		return words;
	}
	
	public static boolean compare(String[] str1, String[] str2) {
		if (str1.length != str2.length) return false;
        for (int i = 0; i < str1.length; i++) {
            if (!str1[i].equals(str2[i])) {
                return false;
            }
        }
        return true;
	}
	
}
