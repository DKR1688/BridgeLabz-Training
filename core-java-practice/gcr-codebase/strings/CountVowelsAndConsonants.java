package strings;

import java.util.*;
public class CountVowelsAndConsonants {

	public static void main(String[] args) {
		Scanner scanner =new Scanner(System.in);
		System.out.println("Enter a string ");
		String str =scanner.nextLine();
		
		int vowels=0;
		int consonants=0;
		
		// here first we will convert all lowercase to uppercase then check for charaters
		str= str.toUpperCase();
		for(int i=0; i<str.length(); i++) {
			char ch=str.charAt(i);
			
			if(ch>='A' && ch<='Z') {
			if(ch=='A' || ch=='E' || ch=='I' || ch=='O' || ch=='U') {
				vowels++;
			}else {
				consonants++;
			}
			}
		}
		
		System.out.println("Number of vowels are "+vowels+". And number of consonants are "+consonants);
		scanner.close();
	}

}
