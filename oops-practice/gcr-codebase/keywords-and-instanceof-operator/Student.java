//University Student Management
class Student {
    //A static variable universityName shared across all students.
    static String universityName ="Global University";
    static int totalStudents =0;

    //Use a final variable rollNumber for each student that cannot be changed.
    final int rollNumber;
    String name;
    String grade;

    Student(int rollNumber, String name, String grade) {
        this.rollNumber =rollNumber;
        this.name =name;
        this.grade =grade;
        totalStudents++; 
    }

    //A static method displayTotalStudents() to show the number of students enrolled.
    static void displayTotalStudents() {
        System.out.println("Total Students Enrolled are- " +totalStudents);
    }

    //Check if a given object is an instance of the Student class before performing operations like displaying or updating grades.
    public void displayStudentDetails(Object object) {
        if (object instanceof Student) {
            System.out.println("University Name is- " +universityName);
            System.out.println("Roll Number is- " +rollNumber);
            System.out.println("Name is- " +name);
            System.out.println("Grade is- " +grade);
            System.out.println();
        } else {
            System.out.println("Not a valid Student object.");
        }
    }

    //Use this in the constructor to initialize name, rollNumber, and grade.
    void updateGrade(Object object, String updatedGrade) {
        if (object instanceof Student) {
            grade =updatedGrade;
            System.out.println("Grade updated is- " +updatedGrade);
        } else {
            System.out.println("Not a valid Student object.");
        }
    }
    
    public static void main(String[] args) {
        Student student1 =new Student(101, "Hemashree", "A");
        Student student2 =new Student(102, "Sharmila", "B");

        Student.displayTotalStudents();

        //displaying student details
        student1.displayStudentDetails(student1);
        student2.displayStudentDetails(student2);

        //update grade
        student2.updateGrade(student2, "C");

        //displaying updated details
        student2.displayStudentDetails(student2);
    }
}
