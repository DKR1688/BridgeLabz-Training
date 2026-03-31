package strings;

import java.util.Scanner;

public class UniqueCharacter {

    //a Method to find the length of the text without using the String method length()
    public static int stringLength(String str) {
        int count=0;
        try {
            while (true) {
                str.charAt(count);
                count++;
            }
        } catch (Exception e) {
            return count;
        }
    }

    //a method to Find unique characters in a string using the charAt() method and return them as a 1D array
    public static char[] findUniqueChars(String str) {
        int len=stringLength(str);
        char[] unique = new char[len];
        int k = 0;

        for (int i=0; i<len; i++) {
            char c =str.charAt(i);
            boolean isUnique = true;

            for (int j=0; j<i; j++) {
                if (str.charAt(j)==c) {
                    isUnique =false;
                    break;
                }
            }
            if (isUnique) unique[k++] =c;
        }

        char[] result =new char[k];
        for (int i=0; i<k; i++) {
        	result[i]=unique[i];
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string ");
        String text = scanner.nextLine();

        char[] uniqueChars = findUniqueChars(text);
        System.out.print("Unique characters: ");
        for (char c : uniqueChars) System.out.print(c + " ");
        scanner.close();
      }
}