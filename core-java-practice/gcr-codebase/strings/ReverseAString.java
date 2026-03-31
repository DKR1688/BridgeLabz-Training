package strings;

import java.util.*;
public class ReverseAString {
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
        String str= sc.nextLine();
        
        // here we are converting string to character array
        char[] characters =str.toCharArray();
        String reverseString = "";

        for (int i=characters.length-1; i>=0; i--) {
            reverseString += characters[i];
        }
        System.out.println("Reversed string is " +reverseString);
        sc.close();
    }
}
