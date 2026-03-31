// a program to find the multiplication table of a number entered by 
// the user from 6 to 9.

import java.util.Scanner;
public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // here we take input from user
        int number = scanner.nextInt();
        
        // And here we are calculating multiplication table from 6 to 9
        for (int i=6; i<=9; i++) {
            int ans = number*i;
            System.out.println(number+" * " +i+ " = "+ans);
        }
        scanner.close();

    }
}