// a program to input marks and 3 subjects physics, chemistry and maths. 
// the percentage and then calculate the grade as per the following guidelines in question


import java.util.*;
public class PercentageGrade {

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        // we are taking input marks for three subjects
        System.out.print("Enter the marks for Physics, Chemistry and Mathematics: ");
        int physics =scanner.nextInt();
        int chemistry =scanner.nextInt();
        int mathematics =scanner.nextInt();

        // calculate total and percentage
        int sumOfMarks = physics+chemistry+mathematics;
        double percentageOfMarks = (sumOfMarks / 300) * 100;

        // here we are calculating grade and remarks on percentage
        String grade;
        String remarks;

        if (percentageOfMarks>=80) {
            grade = "A";
            remarks = "(Level 4, above agency-normalized standards)";
        } else if (percentageOfMarks>=70 && percentageOfMarks<80) {
            grade = "B";
            remarks = "(Level 3, at agency-normalized standards)";
        } else if (percentageOfMarks>=60 && percentageOfMarks<70) {
            grade = "C";
            remarks = "(Level 2, below, but approaching agency-normalized standards)";
        } else if (percentageOfMarks>=50 && percentageOfMarks<60) {
            grade = "D";
            remarks = "(Level 1, well below agency-normalized standards)";
        } else if(percentageOfMarks>=40 && percentageOfMarks<50){
            grade = "E";
            remarks = "(Level 1-, too below agency-normalized standards)";
        }else{
            grade = "R";
            remarks = "(Remedial standards)";
        }

        // here we are priniting percentage, grade and remarks
        // Ensuring the Output clearly shows the Average Mark as well as the Grade and Remarks
        System.out.println("Percentage of Marks: " + percentageOfMarks + "%");
        System.out.println("Grade: " + grade);
        System.out.println("Remarks: " + remarks);

        scanner.close();
    
    }
}