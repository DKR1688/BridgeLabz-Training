package strings;
// a program to demonstrate NumberFormatException

import java.util.*;
public class NumberFormatExceptions{
  
	//a Method to generate the Exception. Use Integer.parseInt(text) to extract number from the text. 
	//This will generate a runtime exception and abruptly stop the program.
    static void generateException(String text) {
        int number =Integer.parseInt(text);
        System.out.println("Number is: " + number);
    }

    //to demonstrate NumberFormatException. Use Integer.parseInt(text) to extract number from the text. 
    //This will generate a runtime exception. Use the try-catch block to handle the NumberFormatException 
    //as well as the generic runtime exception
    static void handleException(String text) {
        System.out.println("\nHandling Exception using try-catch ");
        try {
            int number = Integer.parseInt(text);
            System.out.println("Number is "+number);

        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException is " +e);

        } catch (RuntimeException e) {
            System.out.println("RuntimeException is " +e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string ");
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