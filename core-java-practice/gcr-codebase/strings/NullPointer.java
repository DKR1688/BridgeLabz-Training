// a program to demonstrate NullPointerException. 
import java.util.*;
public class NullPointer{
    public static void main(String[] args) {
        System.out.println("Generating exception to handle: ");
        generateException();

        // Calling method to handles exception
        System.out.println("Handling Exception: ");
        handleException();
    }
    // this method is to generate NullPointerException
    public static void generateException() {
        String characters =null;

        // it will throw NullPointerException
        System.out.println("Length of characters: " +characters.length());
    }

    //this method to handle NullPointerException
    public static void handleException() {
        String characters= null;
        try {
            System.out.println("Length of characters: " + characters.length());
        } 
        catch (NullPointerException e) {
            
            // here we are handling the exception
            System.out.println("NullPointerException present: "+e.getMessage());
        }
    }
}