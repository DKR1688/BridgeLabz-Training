public class PalindromeNumber {

    public static void main(String[] args) {
        int number = 21312;
        System.out.println("Is " + number + " a palindrome? " + isPalindrome(number));
    }

    public static boolean isPalindrome(int x) {

        //if negative numbers are not palindromes
        if (x<0) {
            return false;
        }

        int realNumber = x;
        int reversedNumber = 0;

        while (x!=0) {
            int digit = x % 10;
            reversedNumber = reversedNumber*10+digit;
            x /= 10;
        }

        return realNumber == reversedNumber;
    }

}