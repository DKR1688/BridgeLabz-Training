package strings;

import java.util.*;

public class StringOutOfBoundException {
  
	//a Method to generate the Exception. Access the index using charAt() beyond the length of the String. 
	//This will generate a runtime exception and abruptly stop the program.
    static void generateException(String s) {
        char c =s.charAt(s.length());
        System.out.println(c);
    }

    //a Method to demonstrate StringIndexOutOfBoundsException. Access the index using charAt() beyond 
    //the length of the String. Then write try catch block for Exception while accessing the String method
    static void handleException(String s) {
        System.out.println("\nHandling Exception using try-catch ");
        try {
            char c =s.charAt(s.length());
            System.out.println(c);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Exception is " + e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string is ");
        String input = scanner.nextLine();

        try {
            generateException(input);
        } catch (Exception e) {
            System.out.println("Program stopped due to Runtime Exception");
        }
        handleException(input);
        scanner.close();
    }
}