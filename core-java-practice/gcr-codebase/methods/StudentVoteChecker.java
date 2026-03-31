package methods;

import java.util.*;
public class StudentVoteChecker {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Enter no of students ");
        int n = scanner.nextInt();
        
        //an array of 10 integer elements, loop through the array by take user input for the student's age, 
        //call canStudentVote() and display the result
        int[] ageOfStudent =new int[n];
        for (int i=0; i<ageOfStudent.length; i++) {
            System.out.print("Enter age of "+(i+1)+"student: ");
            ageOfStudent[i] = scanner.nextInt();

            boolean vote= canStudentVote(ageOfStudent[i]);
            if (vote) {
                System.out.println("Student "+(i+1)+" can vote.");
            } else {
                System.out.println("Student "+(i+1)+" cannot vote.");
            }
        }
        scanner.close();
    }

	// method firstly validate the age for a negative number, if a negative return is false cannot vote. 
	//For valid age check for age is 18 or above return true; else return false;
    static boolean canStudentVote(int age) {
    	if(age<0) {
    		return false;
    	}
        if (age>=18) {
            return true;
        } else {
            return false;
        }
    }  
}
