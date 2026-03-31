import java.util.*;
////custom Exception class (it is already defined in another  class)
class InvalidMarkException extends Exception {
    public InvalidMarkException(String messages) {
       super(messages);
    }
}

class Student {
    String name;
    String[] subjects;
    int[] marks;

    Student(String name, String[] subjects, int[] marks) {
        this.name =name;
        this.subjects =subjects;
        this.marks =marks;
    }
}

public class StudentReportWithException {

    //check scores
    public static void validateScores(int[] scores) throws InvalidMarkException {
        for (int score : scores) {
            if (score<0 || score>100) {
                throw new InvalidMarkException("Invalid score is- " + score);
            }
        }
    }

    //calculate average
    public static double calculateAverage(int[] scores) throws InvalidMarkException {
        validateScores(scores);
        int sum = 0;
        for (int s : scores) {
        	sum += s;
        }
        return (double) sum / scores.length;
    }

    //assign grade to students
    public static String assignGrade(double avg) {
        if (avg >=90) {
        	return "A+";
        }else if (avg >=80) {
        	return "A";
        }else if (avg >=70) {
        	return "B";
        }else if (avg >=60) {
        	return "C";
        }else if (avg >=50) {
        	return "D";
        }else {
        	return "F";
        }
    }

    //display report card of students
    public static void displayReport(Student student) {
        try {
            System.out.println();
            System.out.println("Report Card for " +student.name);
            
            for (int i=0; i<student.subjects.length; i++) {
                System.out.println(student.subjects[i] + " is- " +student.marks[i]);
            }
            double avg = calculateAverage(student.marks);
            System.out.println("Average score is- " +avg);
            System.out.println("Grade of student is- " + assignGrade(avg));
        } catch (InvalidMarkException e) {
            System.out.println("Error is- " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //fixed subjects for simplicity
        String[] subjects = {"Hindi", "Math", "Science", "English", "Physics"};

        System.out.println("Enter number of students- ");
        int n = scanner.nextInt();
        scanner.nextLine();

        List<Student> students = new ArrayList<>();
        for (int i=0; i<n; i++) {
            System.out.println("Enter name of student " +(i+1) + "- ");
            String name = scanner.nextLine();

            int[] marks = new int[subjects.length];
            System.out.println("Enter marks of student- ");
            
            for (int j=0; j<subjects.length; j++) {
                System.out.print(subjects[j] + "- ");
                marks[j] = scanner.nextInt();
            }
            scanner.nextLine();
            students.add(new Student(name, subjects, marks));
        }

        //printing report cards
        for (Student student : students) {
            displayReport(student);
        }
    }
}