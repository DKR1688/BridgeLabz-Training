// a program to store the digits of the number in an array 
// and find the largest and second largest element of the array.

import java.util.Scanner;
public class LargestNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number ");
        int number = scanner.nextInt();

        // Define an array to store the digits. 
        // Set the size of the array to maxDigit variable initially set to 10
        int maxDigit = 10;
        int[] digits =new int[maxDigit];

        int index = 0;
        //Use a loop to iterate until the number is not equal to 0.
        //Remove the last digit from the number in each iteration and add it to the array.
        while (number!=0) {
            int digit = number%10;
            digits[index] =digit;
            index++;

            if (index==maxDigit) {
                break;
            }
            number = number / 10;
        }

        int largest = 0;
        int secondLargest = 0;

        // Loop through the array and use conditional statements to find the largest and second largest number in the array
        for (int i=0; i<index; i++) {
            if (digits[i]>largest) {
                secondLargest = largest;
                largest = digits[i];
            } else if (digits[i]>secondLargest && digits[i]!=largest) {
                secondLargest = digits[i];
            }
        }

        System.out.println("Largest digit: " + largest);
        System.out.println("Second largest digit: " + secondLargest);
        scanner.close();
    }
}
