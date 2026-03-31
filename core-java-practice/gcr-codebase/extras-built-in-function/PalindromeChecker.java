import java.util.*;
public class PalindromeChecker {
	public static void main(String[] args) {
        String input = userInput();
        boolean result = isPalindrome(input);
        printPalindrome(input, result);
    }

	//this function is to take input
    static String userInput() {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the string - ");
        String str = scanner.nextLine();
        return str;
    }

    //this function is to check if the string is a palindrome
    static boolean isPalindrome(String str) {
        // first we will remove spaces and convert to lowercase
        str =str.trim().toLowerCase();

        int left=0;
        int right= str.length()-1;

        while (left<right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    static void printPalindrome(String str, boolean ans) {
        if (ans) {
            System.out.println("It is a palindrome.");
        } else {
            System.out.println("It is not a palindrome.");
        }
    }
}
