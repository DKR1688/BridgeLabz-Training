package EmployeeLeaveManageentSystem;

import java.util.*;
public class LeaveManager {
	private Map<Integer, Employee> employees;
	private List<LeaveRequest> requests;

	public LeaveManager() {
		employees = new HashMap<>();
		requests = new ArrayList<>();
	}

	public void addEmployee(Employee e) {
		employees.put(e.getId(), e);
	}

	public void requestLeave(int empId, int days) throws InsufficientLeaveBalanceException {
		Employee e = employees.get(empId);
		if (e == null) {
			System.out.println("Employee not found!");
			return;
		}

		if (e.getLeaveBalance() < days) {
			throw new InsufficientLeaveBalanceException("Employee " + e.getName() + " has insufficient leave balance.");
		}

		LeaveRequest lr = new LeaveRequest(empId, days);
		requests.add(lr);
		System.out.println("Leave request created for " + e.getName() + " (" + days + " days).");
	}

	public void approveLeave(LeaveRequest lr) {
		Employee e = employees.get(lr.getEmployeeId());
		e.deductLeave(lr.getDays());
		lr.approve();
		System.out.println("Leave approved for " + e.getName());
	}

	public void rejectLeave(LeaveRequest lr) {
		lr.reject();
		System.out.println("Leave rejected for employee " + lr.getEmployeeId());
	}

	public List<LeaveRequest> getRequests() {
	    return requests;
	}
	
	public void displayRequests() {
		for (LeaveRequest lr : requests) {
			System.out.println(lr);
		}
	}
}