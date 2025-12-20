// a program to take a number as input find the frequency of 
// each digit in the number using an array and display the frequency of each digit

import java.util.Scanner;

public class FrequencyOfDigit {
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the number ");
        long number =scanner.nextLong();

        // here we will convert number to string to easily extract digits of number
        String numberInStr = Long.toString(number);

        // size is 10 because digits are 0-9
        int[] frequency= new int[10];

        //Loop through the digits array, and increase the frequency of each digit
        for (int i=0; i<numberInStr.length(); i++) {

            // It is to convert character to integer
            int digit = numberInStr.charAt(i)-'0';
            frequency[digit]++;
        }

        System.out.println("Digit frequencies are ");
        for (int i=0; i<10; i++) {
            if (frequency[i]>0) {
                System.out.println("Digit "+i+" occurs "+frequency[i]+" times in number.");
            }
        }
        scanner.close();
    }
}