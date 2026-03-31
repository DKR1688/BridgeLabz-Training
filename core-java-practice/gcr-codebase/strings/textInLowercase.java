//a program to convert the complete text to lowercase and compare the results

import java.util.*;
public class textInLowercase {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.println("Enter the string in uppercase: ");
        String input =scanner.next();

        String builtLower = input.toLowerCase();
        String charAtLower = lowercaseCharAt(input);

        //here we are comparing methods
        boolean equal =compareCharAt(builtLower, charAtLower);

        System.out.println("User input is " + input);
        System.out.println("By using built-in method lowercase are: " + builtLower);
        System.out.println("By using conversion using lowercase are: " + charAtLower);
        System.out.println("Are both answers equal or not " + equal);
        scanner.close();
    }
    //here we are converting each uppercase character to lowercase using charAt() and ASCII values
    public static String lowercaseCharAt(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c>='A' && c<='Z') {
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
