// a program to calculate the average percent mark in PCM

public class AveragePercentMarks {
    public static void main(String[] args) {
        int samMathMark = 94;
        int samPhysicsMark = 95;
        int samChemistryMark = 96;

        // we are calculating average of marks
        int average = ((samMathMark+samPhysicsMark + samChemistryMark)*100) /300;
        System.out.println("Average percent of Sam's marks is: " + average + "%");
    }
}
