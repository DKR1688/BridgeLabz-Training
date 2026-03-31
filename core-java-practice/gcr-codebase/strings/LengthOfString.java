// a program to find and return the length of a string without using the length() method

import java.util.Scanner;

public class LengthOfString {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the string ");
        String string=scanner.next();

        int builtInLength = string.length();

        int userMethodLength = stringLength(string);

        System.out.println("Length by built in method is: "+ builtInLength);
        System.out.println("Length by user defined method is: "+userMethodLength);
        scanner.close();
    }

    public static int stringLength(String str){
        int count=0;
        try{
        while(true) {
            str.charAt(count);
            count++;
        }
    }
    catch(StringIndexOutOfBoundsException e){

        //this exception occur when index goes out of bound
        return count;
        }
    }
}
