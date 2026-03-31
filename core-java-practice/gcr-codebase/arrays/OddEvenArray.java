// a program to save odd and even numbers into odd and even arrays between 1 to the number entered by the user. 
// Finally, print the odd and even numbers array

import java.util.*;
public class OddEvenArray {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        // here we are checking number is natural or not
        if (number<=0) {
            System.out.println("It is not a natural number");
            scanner.close();
            return;
        }

        // it is count of number in array odd or even
        int size = number/2+1;
        int[] oddArray =new int[size];
        int oddIndex = 0;

        int[] evenArray =new int[size];
        int evenIndex = 0;

        // here we are finding odd or even
        for (int i=1; i<=number; i++) {
            if (i%2 == 0) {
                evenArray[evenIndex++] = i;
            } else {
                oddArray[oddIndex++] = i;
            }
        }

        System.out.print("Odd Numbers are ");
        for (int i=0; i<oddIndex; i++) {
            System.out.print(oddArray[i] + " ");
        }

        System.out.println();

        System.out.print("Even Numbers are ");
        for (int i=0; i<evenIndex; i++) {
            System.out.print(evenArray[i] + " ");
        }
        scanner.close();
    }
}