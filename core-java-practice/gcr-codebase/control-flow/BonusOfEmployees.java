// a program to find the bonus of employees based on their years of service.

import java.util.Scanner;
public class BonusOfEmployees {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // user input for salary and years of service
        System.out.print("Enter the salary of the employee: ");
        double salaryOfEmployee = scanner.nextDouble();
        int yearsOfService= scanner.nextInt();
        
        // here we are calculating bonus of employee
        if (yearsOfService>5) {
            double bonus = salaryOfEmployee*0.05;
            System.out.println("The bonus amount is: "+bonus);
        } else {
            System.out.println("No bonus awarded.");
        }
        scanner.close();
    }

}
