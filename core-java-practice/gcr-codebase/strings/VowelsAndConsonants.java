package strings;  
import java.util.*;  
//a program to find vowels and consonants in a string and display the count of  Vowels and Consonants in the string

public class VowelsAndConsonants {  

 // this method is to count vowels and consonants in the input string
 static int[] count(String text) {  
     int vowels=0;
     int consonants=0;  
     
     for (int i=0; i<text.length(); i++) {  
         char ch = text.charAt(i);  

         // Converting uppercase letters to lowercase for easy comparison
         if (ch >='A' && ch<='Z')  
             ch = (char)(ch+32);  

         // Check if the character is a letter
         if (ch>='a' && ch<='z') {  
             // If the character is a vowel
             if ("aeiou".indexOf(ch) != -1)  
                 vowels++;  
             else  
                 consonants++;  
         }  
     }
     return new int[]{vowels, consonants};  
 }  

 public static void main(String[] args) {  
     Scanner scanner = new Scanner(System.in);  
     System.out.print("Enter text: ");  
     String text = scanner.nextLine();  

     int[] result =count(text);  
     System.out.println("Vowels are " +result[0]);  
     System.out.println("Consonants are " +result[1]);  

     scanner.close();  
 }  
}