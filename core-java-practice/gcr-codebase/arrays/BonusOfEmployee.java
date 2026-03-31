//a program to find the bonus of 10 employees based on their years of service 
// and the total bonus amount the company Zara has to pay, along with the old and new salary.

import java.util.Scanner;

public class BonusOfEmployee {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int employees = 10;

        // here we are creating two salary and yearOf service array
        // And bonus and new salary array to store ans
        double[] salaryOfEmployee =new double[employees];
        double[] yearsOfService =new double[employees];
        double[] bonus =new double[employees];
        double[] newSalary =new double[employees];

        // Variables to store totals
        double totalBonus = 0;
        double totalOldSalary = 0;
        double totalNewSalary = 0;

        // a loop to take input from the user. If salary or year of 
        // service is an invalid number then ask the use to enter again. 
        // Note in this case you will have to decrement the index counter
        for (int i=0; i <employees; i++) {
            System.out.println("Enter details for Employee "+(i + 1));

            System.out.print("Salary is ");
            double salary = scanner.nextDouble();

            System.out.print("Years of Service is ");
            double year = scanner.nextDouble();

            if (salary<=0 || year<0) {
                System.out.println("Wrong Input, it must be positive");
                i--;
                continue;
            }

            salaryOfEmployee[i] = salary;
            yearsOfService[i] = year;
        }

        // calculating salary and bonus
        for (int i=0; i<employees; i++) {
            if (yearsOfService[i]>5) {
                bonus[i] = salaryOfEmployee[i]*0.05;
            } else {
                bonus[i] = salaryOfEmployee[i]*0.02;
            }

            newSalary[i] = salaryOfEmployee[i]+bonus[i];

            totalBonus += bonus[i];
            totalOldSalary += salaryOfEmployee[i];
            totalNewSalary += newSalary[i];
        }
        
        // Print the total bonus payout as well as the total old and new salary of all the employees
        System.out.println("\n-- Employee Bonus Report --");
        for (int i=0; i<employees; i++) {
            System.out.println("Employee " + (i+1)+" Old Salary is " +salaryOfEmployee[i]+", Bonus is " +bonus[i]+" and New Salary is " +newSalary[i]);
        }

        System.out.println("\n-- Company Totals --");
        System.out.println("Total Old Salary: " +totalOldSalary);
        System.out.println("Total Bonus Payout: " +totalBonus);
        System.out.println("Total New Salary: " +totalNewSalary);

        scanner.close();
    }
}