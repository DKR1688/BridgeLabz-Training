import java.util.*;
public class EmployeeRolePolymorphism {
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		Employee emp =null;

		while (true) {
			System.out.println("Employee bonus system ---");
			System.out.println("1. Create Manager");
			System.out.println("2. Create Developer");
			System.out.println("3. Show Bonus");
			System.out.println("4. Exit");
			System.out.print("Enter choice: ");
			int choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter manager name- ");
				String mName = sc.next();
				System.out.print("Enter manager salary: ");
				double mSalary = sc.nextDouble();
				emp = new Manager(mName, mSalary);
				System.out.println("Manager created successfully.");
				break;

			case 2:
				System.out.print("Enter developer name: ");
				String dName = sc.next();
				System.out.print("Enter developer salary: ");
				double dSalary = sc.nextDouble();
				emp = new Developer(dName, dSalary);
				System.out.println("Developer created successfully.");
				break;

			case 3:
				if (emp != null) {
					System.out.printf("Employee: %s | Salary: %.2f | Bonus: %.2f%n", emp.getName(), emp.getSalary(),
							emp.getBonus());
				} else {
					System.out.println("No employee created yet.");
				}
				break;

			case 4:
				System.out.println("Exiting program...");
				sc.close();
				return;

			default:
				System.out.println("Invalid choice.");
			}
		}
	}
}

abstract class Employee {
	private final String name;
	private final double salary;
	private final double bonus; //cached bonus for performance

	public Employee(String name, double salary) {
		this.name = name;
		this.salary = salary;
		this.bonus = computeBonus(); //cache at construction
	}

	//abstract method to be implemented by subclasses
	protected abstract double computeBonus();

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	//cached bonus returned directly
	public double getBonus() {
		return bonus;
	}
}

class Manager extends Employee {
	public Manager(String name, double salary) {
		super(name, salary);
	}

	@Override
	protected double computeBonus() {
		return getSalary() * 0.10; //10% of salary
	}
}

// Developer subclass
class Developer extends Employee {
	public Developer(String name, double salary) {
		super(name, salary);
	}

	@Override
	protected double computeBonus() {
		if (getSalary() > 50000) {
			return getSalary() * 0.05; //5% if salary > 50k
		}
		return 0.0;
	}
}
