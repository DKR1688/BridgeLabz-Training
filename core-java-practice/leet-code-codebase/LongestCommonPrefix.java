import java.util.*;

public class LongestCommonPrefix {
    public static void main(String[] args) {

        // it is used to find longest common prefix in an array of strings as object
        LongestCommonPrefix longestCommonPrefix = new LongestCommonPrefix();
        String[] strings = {"flower", "flow", "flight"};

        // here we are calling longestCommonPrefix method or function call
        String ans = longestCommonPrefix.longestCommonPrefix(strings);
        System.out.println("Longest Common Prefix: " + ans);
    }

    // this method is used to find longest common prefix
    public String longestCommonPrefix(String[] strs) {

        // sorting the array of strings
        Arrays.sort(strs);
        String string1 = strs[0];
        String string2 = strs[strs.length - 1];

        // here we are finding length ofcommon prefix between first and last string
        int n = Math.min(string1.length(), string2.length());
        String string3 = "";

        // in it we are comparing characters of both strings
        for (int i = 0; i < n; i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                string3 += string1.charAt(i);
            } else {
                break;
            }
        }

        // returning longest common prefix
        return string3;
    }
}