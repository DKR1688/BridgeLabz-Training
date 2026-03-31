package strings;

import java.util.*;

public class TrimLeadingAndTrilingSpace {

 // this method to find the starting and ending indices of non-space characters
 static int[] findTrimIndices(String text) {
     int start=0;
     int end =text.length()-1;

     while (start<=end && text.charAt(start)==' ') {
    	 start++;
     }

     while (end>=start && text.charAt(end)==' ') {
    	 end--;
     }
     return new int[]{start, end};
 }

 // this method is to extract substring between given start and end indices
 static String substring(String text, int start, int end) {
     String result = "";
     
     // Building the substring character by character
     for (int i = start; i <= end; i++) result += text.charAt(i);
     return result;
 }

 // this method is to compare two strings character by character
 static boolean compareStrings(String s1, String s2) {
     // If lengths are different, strings are not equal
     if (s1.length()!=s2.length()) return false;
     
     for (int i = 0; i < s1.length(); i++)
         if (s1.charAt(i) != s2.charAt(i)) return false;
     
     return true;
 }

 public static void main(String[] args) {
     Scanner scan = new Scanner(System.in);
     System.out.print("Enter the text: ");
     String text = scan.nextLine();

     int[] indices = findTrimIndices(text);
     String manualTrim = substring(text, indices[0], indices[1]);
     String builtInTrim = text.trim();
     
     System.out.println(" Trim By user defined method trim are '" + manualTrim + "'");
     System.out.println("Built-in method trim are '" + builtInTrim + "'");
     System.out.println("Are both equal? " + compareStrings(manualTrim, builtInTrim));
     scan.close();
 }
}