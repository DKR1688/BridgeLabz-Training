package StringBuilder;

import java.util.Scanner;
public class ReverseAString {
    public static void main(String[] args) {
        ReverseAString reverse =new ReverseAString();

        Scanner sc =new Scanner(System.in);
        System.out.print("Enter string to reverse- ");
        String str =sc.nextLine();

        String rev =reverse.reverse(str);
        System.out.println("String after reverse is- " +rev);
        sc.close();
    }

    //this method to reverse the string
    public String reverse(String str) {
        StringBuilder revSb =new StringBuilder();
        for (int i=str.length()-1; i>=0; i--) {
            revSb.append(str.charAt(i));
        }
        return revSb.toString();
    }
}