package StringBuffer;

public class ConcatenateStrings {
    public static void main(String[] args) {
        String[] strings ={ "Hello", "everyone!", "How", "are", "You" };
        //new string buffer object
        StringBuffer sb =new StringBuffer();

        for (String str :strings) {
            sb.append(str).append(" ");
        }
        String ans= sb.toString();
        System.out.println(ans);
    }
}