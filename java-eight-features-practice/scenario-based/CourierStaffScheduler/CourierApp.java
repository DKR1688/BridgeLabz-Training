package CourierStaffScheduler;

public class CourierApp {
	public static void main(String[] args) {
        ShiftScheduler scheduler = new ShiftScheduler();

        Employee e1 = new Employee(1, "Deepak");
        Employee e2 = new Employee(2, "Abhay");
        Employee e3 = new Employee(3, "Abhishek");

        scheduler.addEmployee(e1);
        scheduler.addEmployee(e2);
        scheduler.addEmployee(e3);

        try {
            scheduler.assignShift(e1, ShiftTime.MORNING);
            scheduler.assignShift(e2, ShiftTime.AFTERNOON);
            scheduler.assignShift(e1, ShiftTime.MORNING);
        } catch (ShiftAlreadyAssignedException e) {
            System.out.println(e.getMessage());
        }

        scheduler.displayAssignments();
    }
}
