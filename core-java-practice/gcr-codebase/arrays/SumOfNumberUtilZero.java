// a program to store multiple values in an array up to a maximum of 10 or 
// until the user enters a 0 or a negative number. Show all the numbers as 
// well as the sum of all numbers 

import java.util.Scanner;
public class SumOfNumberUtilZero {
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        double[] numbers = new double[10];
        double sum =0.0;

        // here we are taking input from the user until 
        // he/she enters a 0 or negative number or the array is full
        int index=0;
        while (true) {
            System.out.print("Enter a number ");
            double numberByUser = scanner.nextDouble();

            if (numberByUser<=0) {
                break;
            }
            if (index>=10) {
                System.out.println("Array is full");
                break;
            }

            numbers[index] =numberByUser;
            index++;
        }

        // here we are calculating the sum of all entered numbers
        for (int i = 0; i < index; i++) {
            sum += numbers[i];
        }

        System.out.println("The sum of the 10 numbers is "+sum);
        scanner.close();
    }
}