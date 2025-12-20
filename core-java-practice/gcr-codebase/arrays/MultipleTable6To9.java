// a program to find the multiplication table of a number 
// entered by the user from 6 to 9 and display the result

import java.util.Scanner;
public class MultipleTable6To9 {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        System.out.print("Enter the number: ");
        int number =scanner.nextInt();

        // Array to store multiplication results from 6 to 9
        int[] multiplicationResult = new int[4];

        // here we are calculating multiplication table from 6 to 9
        for (int i=6; i<=9; i++) {
            multiplicationResult[i-6] = number*i;
        }

        // here we are printing the multiplication table from 6 to 9
        for (int i=6; i<=9; i++) {
            System.out.println(number+" * " +i+ " = " +multiplicationResult[i-6]);
        }

        scanner.close();
    }
}