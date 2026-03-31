package strings;
import java.util.*;
// a program to split the text into words and find the shortest and longest strings in a given text
public class SplitTextAndShortestLongestWord {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string: ");
        String text = scanner.nextLine();

        int[] ans= shortestLongest(split(text));
        System.out.println("Shortest length of word is: " + ans[0]);
        System.out.println("Longest length of word is: " + ans[1]);

        scanner.close();
    }
	public static int length(String str) {
        int ch = 0;
        while (true) {
            try { str.charAt(ch++); }
            catch (Exception e) { break; }
        }
        return ch;
    }

    public static String[] split(String text) {
        return text.split(" ");
    }

    public static int[] shortestLongest(String[] words) {
        int min = length(words[0]);
        int max = min;

        for (int i = 1; i < words.length; i++) {
            int l = length(words[i]);
            if (l < min) min = l;
            if (l > max) max = l;
        }
        return new int[]{min, max};
    }

    
}