public class EmployeeManagementSystem {
    public static void main(String[] args) {
        //Employee reference can hold different objects
        Employee employee1 = new FullTimeEmployee(11, "Deepak", 33000);
        Employee employee2 = new PartTimeEmployee(12, "Rajput", 10000, 4, 500);

        employee1.displayDetails();
        System.out.println("Salary of employee is- " + employee1.calculateSalary());
        System.out.println();

        employee2.displayDetails();
        System.out.println("Salary of employee is- " + employee2.calculateSalary());
        System.out.println();

        DepartmentEmp department= new DepartmentEmp(15, "Abhay", 40000);
        department.assignDepartment("HR department");
        department.displayDetails();
        System.out.println(department.getDepartmentDetails());
    }
}

//abstract class
abstract class Employee{
	int employeeId;
    String name;
    double baseSalary;
    
    Employee(int employeeId, String name, double baseSalary) {
        this.employeeId =employeeId;
        this.name =name;
        this.baseSalary =baseSalary;
    }
    
    //getter in encapsulation
    public int getEmployeeId() {
    	return employeeId;
    }
    public String getName() {
    	return name;
    }
    public double getBaseSalary() {
    	return baseSalary;
    }
    
    //abstract method will be implemented in subclass
    public abstract double calculateSalary();
    
    public void displayDetails() {
    	System.out.println("Employee ID is- " +employeeId);
    	System.out.println("Name of employee- " + name);
    	System.out.println("Base salary is- " +baseSalary);
    }
}

//Subclasses
class FullTimeEmployee extends Employee{
	FullTimeEmployee(int employeeId, String name, double baseSalary) {
        super(employeeId, name, baseSalary);
    }
	
	@Override
	public double calculateSalary() {
		return getBaseSalary();  //it is fixed salary
	}
}

class PartTimeEmployee extends Employee{
	int workHours;
	double rate;
	
	PartTimeEmployee(int employeeId, String name, double baseSalary, int workHours, double rate) {
        super(employeeId, name, baseSalary);
        this.workHours = workHours;
        this.rate =rate;
    }
	
	@Override
	public double calculateSalary() {
		return workHours*rate; //daily salary is 500rs
	}
}

//interface
interface Department {
    void assignDepartment(String departmentName);
    String getDepartmentDetails();
}

class DepartmentEmp extends FullTimeEmployee implements Department{
	String departmentName;
	
	DepartmentEmp(int employeeId, String name, double baseSalary){
		super(employeeId, name, baseSalary);
	}
	
	@Override
	public void assignDepartment(String departmentName) {
		this.departmentName =departmentName;
	}
	
	@Override
	public String getDepartmentDetails() {
		return "Department name is- "+departmentName;
	}
}