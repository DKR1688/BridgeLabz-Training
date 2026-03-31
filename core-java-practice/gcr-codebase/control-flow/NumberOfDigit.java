// a program to count the number of digits in an integer.

import java.util.*;
class NumberOfDigit {
  public static void main(String args[]) {
    Scanner sc =new Scanner(System.in);
    int number = sc.nextInt();
    int count = 0;

    // Using a loop to iterate until number is not equal to 0.
    // Removing the last digit from number in each iteration
    // Increasing count by 1 in each iteration.

    while (number!=0) {
      number = number / 10;
      count++;
    }
    System.out.println("The number of digits are " + count);
    sc.close();
  }
}