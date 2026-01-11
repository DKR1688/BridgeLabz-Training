public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        //converting uppercase to lowercase for comparison
        s = s.toLowerCase();

        int left =0;
        int right = s.length()-1;

        while (left<right) {
            //we will skip non-alphanumeric characters
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            //compare characters to be valid palindrome
            if (s.charAt(left) !=s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        ValidPalindrome ans =new ValidPalindrome();

        System.out.println(ans.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(ans.isPalindrome("race a car"));
        System.out.println(ans.isPalindrome(" "));
    }
}