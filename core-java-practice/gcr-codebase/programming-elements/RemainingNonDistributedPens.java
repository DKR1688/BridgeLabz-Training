// a program to find how many pens each student will get if the pens must be divided equally. 
// Also, find the remaining non-distributed pens.

public class RemainingNonDistributedPens {
    public static void main(String[] args) {
        int totalPens = 14;
        int totalStudents = 3;

        // Use Modulus Operator (%) to find the reminder.
        int remainingPens = totalPens % totalStudents;

        // Use Division Operator to find the Quantity of pens
        int pensPerStudent = totalPens / totalStudents;
        

        System.out.println("The Pen Per Student is "+pensPerStudent+" and the remaining pen not distributed is"+remainingPens);
    }
}
