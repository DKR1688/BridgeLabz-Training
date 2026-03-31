// a program to take user input for the age of all 10 students in a class 
// and check whether the student can vote depending on his/her age is greater or equal to 18.

import java.util.*;
public class CanStudentVote {

    public static void main(String[] args) {
        Scanner scannner = new Scanner(System.in);
        int[] studentAge = new int[10];

        // here we are taking input for the age of 10 students
        for(int i=0; i<studentAge.length; i++){
            studentAge[i] =scannner.nextInt();
        }

        int length = studentAge.length;
        // here we are checking whether the student can vote or not
        for(int i=0; i<length; i++){
            if(studentAge[i]<0){
                System.out.println("Invalid age");
            }else if(studentAge[i]>=18){
                System.out.println("The student with the age "+studentAge[i]+" can vote.");
            }else{
                System.out.println("The student with the age "+studentAge[i]+" cannot vote.");
            }
        }
        scannner.close();
    }
}