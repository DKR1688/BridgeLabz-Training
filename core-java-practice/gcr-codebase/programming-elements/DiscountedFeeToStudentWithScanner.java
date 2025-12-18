// a new program similar to the program # 6 but 
// take user input for Student Fee and University Discount

import java.util.Scanner;
public class DiscountedFeeToStudentWithScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create a variable named fee and take user input for fee.
        int fee = scanner.nextInt();

        // Create another variable discountPercent and take user input.
        int discountedPercentage = scanner.nextInt();

        // Compute the discount and assign it to the discount variable.
        int discount = (fee * discountedPercentage) / 100;

        int remainingFee = fee - discount;

        // Compute and print the fee you have to pay by subtracting the discount from the fee.
        System.out.println("The discount amount is "+discount+" and the remaining fee is "+remainingFee);
        scanner.close();
    }
}
