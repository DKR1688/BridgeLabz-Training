// a program to take input marks of students in 3 subjects physics, chemistry, and maths. 
// Compute the percentage and then calculate the grade

import java.util.Scanner;
public class PercentageGrade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students ");
        int number= scanner.nextInt();

        //Create arrays to store marks, percentages, and grades
        int[] physics =new int[number];
        int[] chemistry =new int[number];
        int[] maths =new int[number];
        double[] percentage =new double[number];
        char[] grade =new char[number];

        //Take input for marks of students in physics, chemistry, and maths. 
        //If the marks are negative, ask the user to enter positive values and decrement the index
        for (int i=0; i<number; i++) {
            System.out.println("\nEnter marks for Student "+(i+1));
            physics[i] = scanner.nextInt();
            chemistry[i] = scanner.nextInt();
            maths[i] = scanner.nextInt();

            //Calculating the percentage
            percentage[i] = (physics[i]+chemistry[i]+maths[i]) /3;

            // finding grade of the students
            if (percentage[i] >=80) grade[i] = 'A';
            else if (percentage[i] >=70) grade[i] = 'B';
            else if (percentage[i] >=60) grade[i] = 'C';
            else if (percentage[i] >=50) grade[i] = 'D';
            else if (percentage[i] >=40) grade[i] = 'E';
            else grade[i] = 'R';
        }

        for (int i=0; i<number; i++) {
            System.out.println("Student " + (i + 1));
            System.out.println("Physics is " + physics[i]);
            System.out.println("Chemistry is " +chemistry[i]);
            System.out.println("Maths is " + maths[i]);
            System.out.println("Percentage is " +percentage[i]+"%");
            System.out.println("Grade is " +grade[i]);
        }
        scanner.close();
    }
}