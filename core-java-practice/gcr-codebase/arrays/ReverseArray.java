// a program to take a number as input and reverse the number. 
// To do this, store the digits of the number in an array and display the array in reverse order

import java.util.Scanner;
public class ReverseArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number ");
        int number= scanner.nextInt();

        int temp = number;
        int count=0;
        while (temp>0) {
            count++;
            temp/=10;
        }

        int[] digits =new int[count];
        temp = number;
        for (int i=count - 1; i>=0; i--) {
            digits[i] = temp%10;
            temp/=10;
        }

        // here we will create a reversed array
        int[] reversed =new int[count];
        for (int i=0; i<count; i++) {
            reversed[i] = digits[count-1-i];
        }

        System.out.print("Reversed number in array are: ");
        for (int j :reversed) {
            System.out.print(j);
        }
        scanner.close();
    }
}