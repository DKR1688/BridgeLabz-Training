
public class MinStringLengthAfterRemoveSubstring {
	public static int minLength(String s) {
        StringBuilder sb = new StringBuilder(s);
        boolean remove = true;
        while (remove) {
            remove = false;
            for (int i = 0; i < sb.length() - 1; i++) {
                if ((sb.charAt(i) == 'A' && sb.charAt(i + 1) == 'B') ||
                    (sb.charAt(i) == 'C' && sb.charAt(i + 1) == 'D')) {
                    sb.delete(i, i + 2);
                    remove = true;
                    break;
                }
            }
        }
        return sb.length();
    }

    public static void main(String[] args) {
        String test1 = "ABFCACDB";
        String test2 = "AABBCCDD";
        String test3 = "ABCDE"; 
        String test4 = "XYZ";

        System.out.println("Input- " + test1+ ", Min Length- " +minLength(test1));
        System.out.println("Input- " + test2+ ", Min Length- " +minLength(test2));
        System.out.println("Input- " + test3+ ", Min Length- " +minLength(test3));
        System.out.println("Input- " + test4+ ", Min Length- " +minLength(test4));
    }
}
