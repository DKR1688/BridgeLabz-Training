package strings;

public class LexicographicallyCompare {
	public static void main(String[] args) {
        String string1 = "apple";
        String string2 = "banana";

        int ans= compareStrings(string1, string2);
        if (ans<0) {
            System.out.println("\"" +string1 + "\" comes before \"" + string2 + "\" in lexicographical order.");
        } else if (ans>0) {
            System.out.println("\"" + string2 + "\" comes before \"" + string1 + "\" in lexicographical order.");
        } else {
            System.out.println("Both strings are equal in lexicographical order.");
        }
    }
	
	//a method to compare two strings lexicographically order
    public static int compareStrings(String str1, String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        int minLength = Math.min(length1, length2);

        for (int i=0; i<minLength; i++) {
            char ch1 = str1.charAt(i);
            char ch2 = str2.charAt(i);

            if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }

        // If all characters are same then shorter string comes first
        return length1 - length2;
    }
}
