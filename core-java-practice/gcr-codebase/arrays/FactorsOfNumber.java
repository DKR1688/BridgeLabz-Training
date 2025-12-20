// a program to find the factors of a number taken as user input, 
// store the factors in an array, and display the factors

import java.util.Scanner;

public class FactorsOfNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number =scanner.nextInt();

        int maxFactor = 10;
        int[] factors =new int[maxFactor];
        int index =0;

        // here we are finding the factors of number
        for (int i=1; i<=number; i++) {
            if (number%i == 0) {

                //here we are checking array is full or not
                if (index==maxFactor) {
                    // double the maxFactor for temp array
                    maxFactor*=2;
                    int[] temp = new int[maxFactor];
                    for (int j=0; j<factors.length; j++) {
                        temp[j] = factors[j];
                    }
                    factors=temp;
                }
                // here we will use a array to store factors
                factors[index++] = i;
            }
        }
        System.out.println("Factors of are ");
        for (int i=0; i<index; i++) {
            System.out.print(factors[i] + " ");
        }
        scanner.close();
    }
}