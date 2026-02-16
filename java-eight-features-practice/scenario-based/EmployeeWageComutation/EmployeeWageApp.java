package EmployeeWageComutation;
import java.util.*;
public class EmployeeWageApp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        //Start with Displaying Welcome message
        System.out.println("Welcome to Employee Wage Computation program!");
        System.out.println();

        //UC1 - Check Employee is Present or Absent
        Random random=new Random();
        int emp=random.nextInt(2);
        if (emp==0) {
            System.out.println("Employee is Absent");
        } else {
            System.out.println("Employee is Present");
        }
        System.out.println();

        //UC2 - Calculate Daily Employee Wage
        int wagePerHour =20;
        int fullDayHours =0;
        if (emp==0) {
        	fullDayHours =0;
        } else {
        	fullDayHours =8;
        }
        int dailyWage = wagePerHour*fullDayHours;
        System.out.println("Daily Employee Wages - "+dailyWage);
        System.out.println();

        //UC3 - Add Part-time Employee & Wage
        int partTimeHours = 4;
        int partTimeWage = wagePerHour*partTimeHours;
        System.out.println("Part-time Daily Wage - "+partTimeWage);
        System.out.println();

        //UC4 - Solve using Switch Case Statement
        int empHours;
        int empType = random.nextInt(3);
        switch (empType) {
            case 1:
                empHours =fullDayHours;
                System.out.println("Employee is Full-time");
                break;
            case 2:
                empHours =partTimeHours;
                System.out.println("Employee is Part-time");
                break;
            default:
                empHours =0;
                System.out.println("Employee is Absent");
        }
        int wagePerDay = empHours*wagePerHour;
        System.out.println("Daily wage is - " +wagePerDay);
        System.out.println();

        //UC5 - Calculating Wages for a Month (20 working days)
        int daysPerMonth = 20;
        int wagePerMonth = 0;
        for (int i=1; i<=daysPerMonth; i++) {
            int hours;
            if (empType == 1) {
                hours = fullDayHours;
            } else if (empType == 2) {
                hours = partTimeHours;
            } else {
                hours = 0;
            }
            int dailyWages = hours*wagePerHour;
            wagePerMonth += dailyWages;
            System.out.println("Day "+i+" and for "+hours+" hours, Wage is - " +wagePerDay);
        }
        System.out.println("Total wage for a month is - " +wagePerMonth);
        System.out.println();

        //UC6 - Calculate Wages till total working 100 hours or 20 days
        int totalHours = 0;
        int totalDays = 0;
        int totalWages = 0;
        while (totalHours<100 && totalDays<20) {
        	int type = random.nextInt(3);
            int hours;
            if (type==1) {
                hours = fullDayHours;
            } else if (type==2) {
                hours = partTimeHours;
            } else {
                hours = 0;
            }
            totalDays++;
            totalHours += hours;
            int wage = hours*wagePerHour;
            totalWages += wage;

            System.out.println("Total days - " +totalDays+ "and total hours - "+totalHours);
            System.out.println("For "+hours+" hours, Wage is - " +wage);
        }
        System.out.println("Wages till 100 Hours or 20 Days is - "+totalWages);
        scanner.close();
        
        //UC-8 : wage for multiple Companies
        System.out.println("Total Wage for V-Mart- " + EmployeeWageApp.computeWage(20, 20, 100));
        System.out.println("Total Wage for Reliance- " + EmployeeWageApp.computeWage(25, 22, 120));
        
        
      //UC-13 : query total wage by company
        EmpWageBuilder builder=new EmpWageBuilder();
        builder.addCompanyEmpWage("D-Mart", 20, 20, 100);
        builder.addCompanyEmpWage("Reliance", 25, 22, 120);
        builder.addCompanyEmpWage("BigBazaar", 30, 18, 90);

        builder.computeEmpWage();

        System.out.println("Queried Total Wage for Reliance: " +builder.getTotalWage("Reliance"));
    }
    
    //UC-7 : refactor to class method to compute wage
    static final int IS_PART_TIME = 1;
    static final int IS_FULL_TIME = 2;
    public static int computeWage(int wagePerHour, int workingDays, int maxHoursPerMonth) {
    	int totalEmpHrs = 0;
    	int totalWorkingDays = 0;
        while (totalEmpHrs <= maxHoursPerMonth && totalWorkingDays < workingDays) {
            totalWorkingDays++;
            int empCheck = (int) Math.floor(Math.random() * 3);
            int empHrs = 0;
            switch (empCheck) {
                case IS_PART_TIME: 
                	empHrs = 4; 
                	break;
                case IS_FULL_TIME: 
                	empHrs = 8; 
                	break;
                default: empHrs = 0;
            }
            totalEmpHrs += empHrs;
        }
        return totalEmpHrs * wagePerHour;
    }
   
}
