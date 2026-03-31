package strings;

import java.util.Scanner;
public class PalindromeText {

    // a method to compare the characters from the start and end of the string to determine whether the text is palindrome
    public static boolean isPalindromeIterative(String str) {
        int start=0;
        int end =str.length()-1;
        while (start<end) {
            if (str.charAt(start) != str.charAt(end)) {
            	return false;
            }
            start++;
            end--;
        }
        return true;
    }

    //a  method to compare the characters from the start and end of the text passed as parameters using recursion
    public static boolean isPalindromeRecursive(String str, int start, int end) {
        if (start>= end) {
        	return true;
        }
        if (str.charAt(start) != str.charAt(end)) {
        	return false;
        }
        return isPalindromeRecursive(str, start+1, end-1);
    }

    //a Method to compare the characters from the start and end of the text using character arrays.
    public static boolean isPalindromeArray(String str) {
        char[] arr = str.toCharArray();
        int n=arr.length;
        for (int i=0; i<n / 2; i++) {
            if (arr[i] != arr[n - i - 1]) {
            	return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string:");
        String text = scanner.nextLine();

        System.out.println("Palindrome Check from logic 1 " + isPalindromeIterative(text));
        System.out.println("Palindrome Check from logic 2 " + isPalindromeRecursive(text, 0, text.length() - 1));
        System.out.println("Palindrome Check from logic 3 " + isPalindromeArray(text));
        scanner.close();
    }
}