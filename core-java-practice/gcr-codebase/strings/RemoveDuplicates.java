package strings;

import java.util.*;
public class RemoveDuplicates {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a string ");
        String str =sc.next();
        
        
        String modifiedStr = removeDuplicate(str);
        System.out.println("After removing duplicates string is " + modifiedStr);
        sc.close();
    }
	//a method to remove duplicate characters
    public static String removeDuplicate(String str) {
        StringBuilder sb= new StringBuilder();

        //this array is to mark seen characters
        boolean[] seen = new boolean[256];
        for (char ch : str.toCharArray()) {
            if (!seen[ch]) {
                seen[ch] = true;
                sb.append(ch);            }
        }
        return sb.toString();
    }
}
