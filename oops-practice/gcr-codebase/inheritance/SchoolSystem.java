
public class SchoolSystem {
	public static void main(String[] args) {
		Teacher teacher = new Teacher("Deepak", 40, "Math");
		Student student = new Student("Abhay", 16, "10th Grade");
		Staff staff = new Staff("Rishabh", 35, "Administration");

		teacher.displayRole();
		student.displayRole();
		staff.displayRole();
	}
}

//Superclass
class Person {
	String name;
	int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
}

class Teacher extends Person {
	String subject;

	public Teacher(String name, int age, String subject) {
		super(name, age);
		this.subject = subject;
	}

	public void displayRole() {
		System.out.println("Teacher name  is- " + name + ",\n Subject is- " + subject);
	}
}

class Student extends Person {
	String grade;

	public Student(String name, int age, String grade) {
		super(name, age);
		this.grade = grade;
	}

	public void displayRole() {
		System.out.println("Student name is- " + name + ",\n Grade is- " + grade);
	}
}

class Staff extends Person {
	String department;

	public Staff(String name, int age, String department) {
		super(name, age);
		this.department = department;
	}

	public void displayRole() {
		System.out.println("Staff- " + name + ",\n Department is- " + department);
	}
}