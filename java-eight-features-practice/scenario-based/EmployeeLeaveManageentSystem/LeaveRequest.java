package EmployeeLeaveManageentSystem;

public class LeaveRequest {
	private int employeeId;
	private int days;
	private String status; // Pending, Approved, Rejected

	public LeaveRequest(int employeeId, int days) {
		this.employeeId = employeeId;
		this.days = days;
		this.status = "Pending";
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public int getDays() {
		return days;
	}

	public String getStatus() {
		return status;
	}

	public void approve() {
		status = "Approved";
	}

	public void reject() {
		status = "Rejected";
	}

	@Override
	public String toString() {
		return "LeaveRequest{employeeId=" + employeeId + ", days=" + days + ", status='" + status + "'}";
	}
}