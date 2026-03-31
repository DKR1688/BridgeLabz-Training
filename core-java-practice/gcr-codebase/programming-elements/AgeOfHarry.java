// a program to find the age of Harry if the birth year is 2000. 
// Assume the Current Year is 2024

public class AgeOfHarry {

    public static void main(String[] args) {
        int birthYear = 2000;
        int currentYear = 2024; // we are assuming current year is 2024

        // calculating age of Harry
        int age  = currentYear - birthYear;
        System.out.println("Harry's age in "+currentYear+" is: " + age);
    }
}