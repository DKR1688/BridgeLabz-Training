package strings;

import java.util.*;

public class ArrayIndexOutOfBound {
	
	//a Method to generate the Exception. Here access index larger then the length of the array. 
	//This will generate a runtime exception and abruptly stop the program. 
    static void generateException(String[] names) {
        System.out.println("Generating Exception ");
        System.out.println(names[names.length]);
    }

    //the Method to demonstrate ArrayIndexOutOfBoundsException. Here access index larger then 
    //the length of the array. This will generate a runtime exception. Use the try-catch block to handle 
    //the ArrayIndexOutOfBoundsException and the generic runtime exception
    static void handleException(String[] names) {
        System.out.println("\nHandling Exception using try-catch...");
        try {
            System.out.println(names[names.length]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException Caught: " + e);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException Caught: " + e);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of names: ");
        int size = scanner.nextInt();
        scanner.nextLine();
        String[] names = new String[size];

        for (int i=0; i<size; i++) {
            System.out.print("Enter name " +(i+1)+ ": ");
            names[i] = scanner.nextLine();
        }

        try {
            generateException(names);
        } catch (Exception e) {
            System.out.println("Program stopped due to Runtime Exception");
        }

        handleException(names);
        scanner.close();
    }
}