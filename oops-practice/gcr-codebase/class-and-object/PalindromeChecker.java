public class PalindromeChecker {
	String text;

    PalindromeChecker(String text) {
        this.text = text;
    }

    //this method is to check if text is palindrome
    public boolean isPalindrome() {
        String removeSpace =text.trim().toLowerCase();
        int left = 0;
        int right = removeSpace.length() - 1;

        while (left<right) {
            if (removeSpace.charAt(left) != removeSpace.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    //this method is to display ans
    public void display() {
        if (isPalindrome()) {
            System.out.println(text+" is palindrome");
        } else {
            System.out.println(text+" is not Palindrome");
        }
    }
    
    public static void main(String[] args) {
        PalindromeChecker palindrome1 =new PalindromeChecker("A man a plan a canal Panama");
        PalindromeChecker palindrome2 =new PalindromeChecker("Hello");

        palindrome1.display();
        palindrome2.display();
    }
}
