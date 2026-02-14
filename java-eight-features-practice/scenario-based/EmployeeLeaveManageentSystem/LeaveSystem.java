package EmployeeLeaveManageentSystem;

public class LeaveSystem{
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee e1 = new Employee(1, "Deepak", 10);
        Employee e2 = new Employee(2, "Abhay", 3);

        manager.addEmployee(e1);
        manager.addEmployee(e2);

        try {
            manager.requestLeave(1, 5);
            manager.requestLeave(2, 4);
        } catch (InsufficientLeaveBalanceException ex) {
            System.out.println(ex.getMessage());
        }

        manager.approveLeave(manager.getRequests().get(0));
        manager.displayRequests();
    }
}