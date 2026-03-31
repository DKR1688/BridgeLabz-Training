public class Employee{
	//Attributes of class
	String name;
	int id;
	double salary;
	
	//Constructor to define attributes
	Employee(String name, int id, double salary){
		this.name=name;
		this.id=id;
		this.salary=salary;
	}
	
	// this method printing info of enmployee
	void printInfo() {
		System.out.println("Employee Name: "+name);
        System.out.println("Employee id: "+id);
        System.out.println("Employee Salary: "+salary);
	}
	
	public static void main(String[] args) {
		// here we will create a object of sub class to call method
		Employee employee =new Employee("Deepak", 121, 33000);
		employee.printInfo();
	}
}