package strings;

import java.util.*;
public class PalindromeStringCheck {
	public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the string ");
        String str =scanner.nextLine();

        // Converting to lowercase and Check if the string is a palindrome
        str =str.toLowerCase();
        if (isPalindrome(str)) {
            System.out.println("The string is a palindrome.");
        } else {
            System.out.println("The string is not a palindrome.");
        }
        scanner.close();
    }

    // a method to check palindrome
    static boolean isPalindrome(String str) {
        int left=0;
        int right=str.length()-1;

        while (left<right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
