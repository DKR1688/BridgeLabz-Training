package com.junit;

public class StringUtilityMethods {
	public static String reverse(String str) {
		if(str==null) {
			return null;
		}
		return new StringBuilder(str).reverse().toString();
	}
	
	public static boolean isPalindrome(String str) {
		if(str==null) {
			return false;
		}
		String palindrome=reverse(str);
		return str.equalsIgnoreCase(palindrome);
	}
	
	public static String toUpperCase(String str) {
        if (str==null) {
        	return null;
        }
        return str.toUpperCase();
    }
}
