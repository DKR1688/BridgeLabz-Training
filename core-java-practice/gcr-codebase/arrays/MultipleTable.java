// a program to print a multiplication table of a number.

import java.util.Scanner;
public class MultipleTable {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number ");
        int number = scanner.nextInt();
        int[] multiplicationTable =new int[10];

        for (int i=1; i<=10; i++) {
            multiplicationTable[i-1] = number*i;
        }

        // here we are printing the multiplication table
        for (int i=1; i<=10; i++) {
            System.out.println(number+" * "+i+" = "+multiplicationTable[i - 1]);
        }

        scanner.close();
    }
}