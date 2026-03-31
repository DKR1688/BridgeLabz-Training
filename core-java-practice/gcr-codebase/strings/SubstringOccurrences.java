package strings;

import java.util.*;
public class SubstringOccurrences {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string and substring ");
        String string = scanner.nextLine();
        String subString =scanner.nextLine();

        int occurrences = countOccurrences(string, subString);
        System.out.println("The substring occurs "+occurrences+" times in the string");
        scanner.close();
    }
	
	//a method to count occurrences of a substring
    public static int countOccurrences(String str, String subStr) {
        int count=0;
        
        int i=0;
        while ((i=str.indexOf(subStr,i)) != -1) {
            count++;
            
            //Moving past the last found substring
            i= i+subStr.length(); 
        }
        return count;
    }
}
