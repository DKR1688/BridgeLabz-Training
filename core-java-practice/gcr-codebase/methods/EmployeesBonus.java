package methods;

import java.util.*;
public class EmployeesBonus {
	public static void main(String[] args) {
        int number = 10;

        int[][] salaryAndService = generateSalaryAndService(number);
        double[][] newSalaryAndBonus = calculateNewSalaryAndBonus(salaryAndService);

        displayTable(salaryAndService, newSalaryAndBonus);
    }

    //a Method to determine the Salary and years of service and return the same. 
	//Use the Math.random() method to determine the 5-digit salary for each employee and 
	//also use the random method to determine the years of service. Define 2D Array to save the salary and years of service.
    public static int[][] generateSalaryAndService(int n) {
        Random rand = new Random();
        int[][] data = new int[n][2];

        for (int i = 0; i < n; i++) {
            data[i][0] = 10000 + rand.nextInt(90000);

            data[i][1] = 1 + rand.nextInt(10);
        }
        return data;
    }

    //a Method to calculate the new salary and bonus based on the logic defined above and 
    //return the new 2D Array of the latest salary and bonus amount 
    public static double[][] calculateNewSalaryAndBonus(int[][] data) {
        int n = data.length;
        double[][] result = new double[n][2];

        for (int i = 0; i < n; i++) {
            int salary = data[i][0];
            int years = data[i][1];
            double bonus = (years > 5) ? salary * 0.05 : salary * 0.02;
            double newSalary = salary + bonus;

            result[i][0] = newSalary;
            result[i][1] = bonus;
        }

        return result;
    }

    //a Method to Calculate the sum of the Old Salary, the Sum of the New Salary, and 
    //the Total Bonus Amount and display it in a Tabular Format
    public static void displayTable(int[][] data, double[][] result) {
        double totalOld = 0;
        double totalNew = 0;
        double totalBonus = 0;

        System.out.println("Emp\tOld Salary\tYears\tBonus\tNew Salary");

        for (int i = 0; i < data.length; i++) {
            int oldSalary = data[i][0];
            int years = data[i][1];
            double bonus = result[i][1];
            double newSalary = result[i][0];

            totalOld += oldSalary;
            totalNew += newSalary;
            totalBonus += bonus;

            System.out.println((i+1)+""+ oldSalary+""+ years+""+ bonus+"" +newSalary);
        }

        System.out.println("Total are "+
                totalOld+" "+ totalBonus+" "+ totalNew);
    }
}
