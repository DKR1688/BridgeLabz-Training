// a program to check whether a person can vote, depending on whether his/her age is greater than or equal to 18.

import java.util.Scanner;
public class PersonCanVote {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ageOfPerson= scanner.nextInt();

        // checking if the person's age is greater than or equal to 18
        // if yes then the person can vote otherwise cannot vote
        if (ageOfPerson>=18) {
            System.out.println("The person's age is "+ageOfPerson+" and can vote.");
        } else {
            System.out.println("The person's age is "+ageOfPerson+" and cannot vote.");
        }
        scanner.close();
    }
}