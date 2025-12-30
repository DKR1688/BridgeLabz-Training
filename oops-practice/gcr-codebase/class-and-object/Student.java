public class Student {
	String name;
    String rollNumber;
    double[] marks;

    public Student(String name, String rollNumber, double[] marks) {
        this.name =name;
        this.rollNumber =rollNumber;
        this.marks =marks;
    }

    //this method is to calculate grade based on average marks
    public char calculateGrade() {
        double sum=0;
        for (double mark : marks) {
            sum+=mark;
        }
        double average = sum/marks.length;

        if (average>=80) {
            return 'A';
        } else if (average>=70) {
            return 'B';
        } else if (average>=60) {
            return 'C';
        } else if (average>=50) {
            return 'D';
        } else {
            return 'F';
        }
    }
    
    //this method is to display student details and grade
    public void display() {
        System.out.println("Student Name- " +name);
        System.out.println("Student RollNumber- " +rollNumber);
        System.out.println("Student Marks-");
        for (int i=0; i<marks.length; i++) {
            System.out.println("Mark" + (i+1) + "- " +marks[i]);
        }
        System.out.println("Grade " +calculateGrade());
    }

    public static void main(String[] args) {
        double[] marks1 ={80.0, 70.0, 75.0};
        Student s1 =new Student("Thamarai", "ECE001", marks1);

        double[] marks2 ={60.0, 65.0, 50.0};
        Student s2 =new Student("Kannan", "CSC002", marks2);

        s1.display();
        s2.display();
    }
}
