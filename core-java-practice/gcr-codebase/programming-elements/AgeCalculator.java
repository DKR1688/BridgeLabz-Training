import java.util.Scanner;
public class AgeCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int birthYear = 2000;
        System.out.print("Enter the current year: ");
        int currentYear = sc.nextInt(); // we are assuming current year is as input

        int age  = currentYear - birthYear;
        System.out.println("Harry's age in "+currentYear+" is: " + age);
        sc.close();
    }
}