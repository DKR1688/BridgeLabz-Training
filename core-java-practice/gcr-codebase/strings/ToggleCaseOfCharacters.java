package strings;

import java.util.*;
public class ToggleCaseOfCharacters {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the string ");
        String string =scanner.nextLine();
        
        String ans = toggleCase(string);
        System.out.println("Toggled String is - " +ans);
        scanner.close();
    }

    // a method to toggle case of each character
    public static String toggleCase(String str) {
        StringBuilder toggleString = new StringBuilder();

        for (int i=0; i<str.length(); i++) {
            char ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                toggleString.append(Character.toLowerCase(ch));
            }
            else if (Character.isLowerCase(ch)) {
                toggleString.append(Character.toUpperCase(ch));
            }
            else {
                toggleString.append(ch);
            }
        }
        return toggleString.toString();
    }


}
