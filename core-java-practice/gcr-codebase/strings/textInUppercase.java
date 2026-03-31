// a program to convert the complete text to uppercase and compare the results
import java.util.*;
public class textInUppercase {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the string: ");
        String input =scanner.next();

        String builtUpper = input.toUpperCase();
        String charAtUpper = uppercaseCharAt(input);

        //here we are comparing methods
        boolean equal =compareCharAt(builtUpper, charAtUpper);

        System.out.println("User input is " + input);
        System.out.println("By using built-in method uppercase are: " + builtUpper);
        System.out.println("By using conversion using uppercase are: " + charAtUpper);
        System.out.println("Are both answers equal or not " + equal);
        scanner.close();
    }
    //here we are converting each lowercase character to uppercase using charAt() and ASCII values
    public static String uppercaseCharAt(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c>='a' && c<='z') {
                c = (char) (c-32);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    // comparing two strings using charAt()
    public static boolean compareCharAt(String str1, String str2) {
        if (str1==null || str2==null){
            return false;
        }
        if (str1.length() != str2.length()){
            return false;
        }
        for (int i=0; i<str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)){
                return false;
            }
        }
        return true;
    }
}
