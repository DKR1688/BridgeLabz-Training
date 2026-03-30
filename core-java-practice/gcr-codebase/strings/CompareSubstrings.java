// A program to create a substring from a String using the charAt() method.
// Also, use the String built-in method substring() to find the substring of
// the text. Finally, compare the two strings and display the results.

import java.util.*;

public class CompareSubstrings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string, starting and ending index ");
        String string = scanner.next();
        int start = scanner.nextInt();
        int end = scanner.nextInt();

        // Creating substring using charAt()
        String charAtString = createSubstring(string, start, end);

        // Creating substring using built-in substring() method
        String builtedSubString = string.substring(start, end);

        // Comparing both substrings using compare method
        boolean compareSubstrings = compare(charAtString, builtedSubString);

        System.out.println("The substring after comparison is " + compareSubstrings);
        scanner.close();
    }

    // this method to create substring manually using charAt()
    public static String createSubstring(String string, int start, int end) {
        // we are using StringBuilder to build substring
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = start; i < end; i++) {
            stringBuilder.append(string.charAt(i));
        }
        // Returning the constructed substring
        return stringBuilder.toString();
    }

    // this method to compare two strings using charAt()
    public static boolean compare(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false; // Mismatch found
            }
        }
        //if alll characters matched, strings are equal
        return true;
    }
}