public class Student {
	int rollNumber;
	String name;
	double CGPA;
	
	Student(int rollNumber, String name, double CGPA){
		this.rollNumber =rollNumber;
        this.name =name;
        this.CGPA =CGPA;
	}
	
	//getter to get  value
	double getCGPA() {
		return CGPA;
	}
	
	//setter to assign value
	void setCGPA(double CGPA) {
		if(CGPA>=0.0 && CGPA<=10.0) {
			this.CGPA=CGPA;
		}else {
			System.out.println("CGPA must be between 0 to 10.");
		}
	}
	
	void displayInfo() {
		System.out.println("Roll number is- " +rollNumber);
        System.out.println("Name is- " +name);
        System.out.println("CGPA is- " +CGPA);
	}
	
	public static void main(String[] args) {
        Student s1 =new Student(42, "Deepak", 7.6);
        s1.displayInfo();
        
        s1.setCGPA(9.2);
        System.out.println("Updated CGPA is- " +s1.getCGPA());

        System.out.println();
        PostgraduateStudent pg1 = new PostgraduateStudent(17, "Rajput", 7.0, "Computer Science");
        pg1.displayPostInfo();
    }
}

class PostgraduateStudent extends Student{
	String subject;
	
	PostgraduateStudent(int rollNumber, String name, double CGPA, String subject){
		super(rollNumber, name, CGPA);
		this.subject =subject;
	}
	
	//Demonstrating access to protected 'name'
	void displayPostInfo() {
		System.out.println("Postgraduate student name is- " +name);
        System.out.println("Subject is- " +subject);
        System.out.println("CGPA is- " + getCGPA());
    }
}

