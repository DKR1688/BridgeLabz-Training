package StringBuilder;

import java.util.Scanner;
public class RemoveDuplicatesFromString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter string to remove duplicate- ");
        String str = sc.nextLine();

        String remove =remove(str);
        System.out.println("String after remove duplicate is- " +remove);
        sc.close();
    }

    //method to remove the duplicate from string
    public static String remove(String str) {
        StringBuilder sb =new StringBuilder();

        for (int i=0; i<str.length(); i++) {
            char currentChar =str.charAt(i);
            boolean alreadyExists =false;

            //checking if our sb already contains this character
            for (int j=0; j<sb.length(); j++) {
                if (sb.charAt(j) ==currentChar) {
                    alreadyExists =true;
                    break;
                }
            }

            //we append if it wasn't found
            if (!alreadyExists) {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }
}
