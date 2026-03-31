package strings;

import java.util.*;

public class IllegalArgumentExceptions{
  
	//a Method to generate the Exception. Here use the subString() and set the start index to be greater than the end index. 
	//This will generate a runtime exception and abruptly stop the program. 
    static void generateException(String str) {
        int start =5;
        int end =2;

        if (start>end) {
            throw new IllegalArgumentException("Start index cannot be greater than end index");
        }
        String result = str.substring(start, end);
        System.out.println(result);
    }

    //the Method to demonstrate IllegalArgumentException. Here use the subString() and 
    //set the start index to be greater than the end index. This will generate a runtime exception. 
    //Use the try-catch block to handle the IllegalArgumentException and the generic runtime exception
    static void handleException(String str) {
        System.out.println("\nHandling Exception using try-catch ");
        int start =5;
        int end =2;

        try {
            if (start>end) {
                throw new IllegalArgumentException("Start index cannot be greater than end index");
            }
            String result =str.substring(start, end);
            System.out.println(result);

        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException is " + e.getMessage());

        } catch (RuntimeException e) {
            System.out.println("RuntimeException is " + e);
        }
    }

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the string: ");
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
