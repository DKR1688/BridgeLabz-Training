// Program to compare two strings using the charAt() method
// and verify the result with the built-in String equals() method

import java.util.*;

public class CompareStrings {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string1 = scanner.next();
        String string2 = scanner.next();

        // this is to compare strings manually using charAt() method
        boolean charAt = compare(string1, string2);

        // this is to compare strings using built-in equals() method
        boolean equals = string1.equals(string2);

        if (charAt == equals) {
            System.out.println("Both strings are same.");
        } else {
            System.out.println("Both strings are not same, have different characters");
        }
        scanner.close();
    }

    //this method to compare two strings character by character
    public static boolean compare(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        // Comparing each character of both strings
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}