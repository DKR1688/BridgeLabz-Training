// Program to return all the characters in a string using a user-defined method,
// compare the result with the String built-in toCharArray() method, and display the result.

import java.util.*;

public class CompareCharacters {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String string =scanner.nextLine();

        // Calling user-defined method to get characters of the string
        char[] userCharacter = getCharacters(string);

        // Using built-in toCharArray() method to get characters of the string
        char[] builtCharacter = string.toCharArray();

        // Comparing both character arrays using user-defined method or build function same or not
        boolean equals = CompareCharArray(userCharacter, builtCharacter);

        System.out.println("Characters by user-defined method are ");
        for (char c: userCharacter) {
            System.out.print(c+ " ");
        }
        System.out.println(); // Move to next line

        // Display characters obtained by built-in method
        System.out.println("Characters by built-in method are: ");
        for (char c : builtCharacter) {
            System.out.print(c + " ");
        }
        System.out.println(); // Move to next line

        // Display whether both arrays are equal or not
        System.out.println("Both arrays are equal or not: " + equals);
        scanner.close();
    }

    // User-defined method to convert string into character array
    public static char[] getCharacters(String str) {
        // Create a new char array of same length as string
        char[] characters = new char[str.length()];

        // Copy each character from string into char array
        for (int i=0; i<str.length(); i++) {
            characters[i] = str.charAt(i);
        }
        return characters; // Return the char array
    }

    // this method is to compare two character arrays
    public static boolean CompareCharArray(char[] array1, char[] array2) {
        // If lengths are different, arrays are not equal
        if (array1.length!=array2.length) {
            return false;
        }

        // Compare each character one by one
        for (int i=0; i<array1.length; i++) {
            if (array1[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
}