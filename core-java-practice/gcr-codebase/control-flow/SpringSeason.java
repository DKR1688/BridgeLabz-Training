// a program SpringSeason that takes two int values month and day from the command line and 
// prints “Its a Spring Season” otherwise prints “Not a Spring Season”.

import java.util.Scanner;
public class SpringSeason {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // here we are taking month and day as input from user
        System.out.print("Enter month and then day: ");
        int month = scanner.nextInt();
        int day = scanner.nextInt();

        // here we are checking the condition for Spring Season
        // Spring Season is from March 20 to June 20
        if ((month == 3 && day >= 20 && day <= 31) || 
            (month == 4 && day >= 1 && day <= 30) || 
            (month == 5 && day >= 1 && day <= 31) || 
            (month == 6 && day >= 1 && day <= 20)) {
            System.out.println("It's a Spring Season");
        }else{
            System.out.println("Not a Spring Season");
        }
        scanner.close();
    }
}

