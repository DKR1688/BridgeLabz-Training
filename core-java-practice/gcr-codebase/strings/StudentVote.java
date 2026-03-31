package strings;
// a program to take user input for the age of all 10 students in a class 
//and check whether the student can vote depending on his/her age is greater or equal to 18.

import java.util.*;

public class StudentVote {
  static int[] generateAges(int n) {
        int[] ages = new int[n];
        for (int i = 0; i < n; i++) {
            ages[i] = 10 + (int)(Math.random() * 91);
        }
        return ages;
    }

  	//Create a method to define the random 2-digit age of several students provided 
  	//as method parameters and return a 1D array of ages of n students
    static String[][] checkVoting(int[] ages) {
        String[][] table = new String[ages.length][2];
        for (int i = 0; i < ages.length; i++) {
            table[i][0] = String.valueOf(ages[i]);
            table[i][1] = (ages[i] >= 18) ? "true" : "false";
        }
        return table;
    }

    // this method is to display table of who can vote
    static void displayTable(String[][] table) {
        System.out.println("Age\tCan Vote");
        for (int i = 0; i < table.length; i++)
            System.out.println(table[i][0] + "\t" + table[i][1]);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter number of students: ");
        int n = scan.nextInt();

        int[] ages = generateAges(n);
        String[][] result = checkVoting(ages);

        displayTable(result);
        scan.close();
    }
}