package LexicalTwist;
import java.util.*;
public class LexicalTwist {
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the first word- ");
		String firstWord =scanner.nextLine();
		
		System.out.println("Enter the second word- ");
		String secondWord =scanner.nextLine();
		
		//if string contain more than one word then returning after validation
		if(firstWord.contains(" ")) {
			System.out.println("First word is invalid.");
			return;
		}else if(secondWord.contains(" ")) {
			System.out.println("Second word is invalid.");
			return;
		}
		
		//if the second word is a reversed  of the first word
		String reverseWord =new StringBuilder(firstWord).reverse().toString();
		if(reverseWord.equalsIgnoreCase(secondWord)) {
			String replaceWord =secondWord.toLowerCase().replaceAll("[aeiou]", "@");
			System.out.println(replaceWord);
		}else {
			String combineWords =(firstWord+secondWord).toUpperCase();
			
			int vowels=0;
			int consonants=0;
			for(int i=0; i<combineWords.length(); i++) {
				if(combineWords.charAt(i)=='A' ||combineWords.charAt(i)=='E' ||combineWords.charAt(i)=='I' ||combineWords.charAt(i)=='O' ||combineWords.charAt(i)=='U') {
					vowels++;
				}else {
					consonants++;
				}
			}
			
			if(vowels>consonants) {
				String str="";
				for(int i=0; i<combineWords.length(); i++) {
					char ch =combineWords.charAt(i);
                    if ("AEIOU".indexOf(ch)!= -1 && str.indexOf(ch)== -1) {
                        str +=ch;
                        if (str.length()==2) {
                        	break;
                        }
                    }
				}
				System.out.println(str);
			} else if(consonants>vowels) {
				String str="";
				for(int i=0; i<combineWords.length(); i++) {
					char ch =combineWords.charAt(i);
                    if ("AEIOU".indexOf(ch)== -1 && ch>='A' && ch<='Z' && str.indexOf(ch)== -1) {
                        str +=ch;
                        if (str.length() == 2) {
                        	break;
                        }
					}
				}
				System.out.println(str);
			}else {
				System.out.println("Vowels and consonants are equal.");
			}
		}
		scanner.close();
	}
}
