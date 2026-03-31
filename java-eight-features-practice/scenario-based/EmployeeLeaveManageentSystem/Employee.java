package EmployeeLeaveManageentSystem;

public class Employee {
	private int id;
	private String name;
	private int leaveBalance;

	public Employee(int id, String name, int leaveBalance) {
		this.id = id;
		this.name = name;
		this.leaveBalance = leaveBalance;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getLeaveBalance() {
		return leaveBalance;
	}

	public void deductLeave(int days) {
		leaveBalance -= days;
	}

	@Override
	public String toString() {
		return "Employee{id=" + id + ", name='" + name + "', balance=" + leaveBalance + "}";
	}
}
