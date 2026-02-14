package CourierStaffScheduler;
import java.util.*;

public class ShiftScheduler {
    private List<Employee> employees;
    private Map<ShiftTime, List<Employee>> shiftAssignments;

    public ShiftScheduler() {
        employees = new ArrayList<>();
        shiftAssignments = new HashMap<>();
        for (ShiftTime shift : ShiftTime.values()) {
            shiftAssignments.put(shift, new ArrayList<>());
        }
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void assignShift(Employee e, ShiftTime shift) throws ShiftAlreadyAssignedException {
        List<Employee> assigned = shiftAssignments.get(shift);
        if (assigned.contains(e)) {
            throw new ShiftAlreadyAssignedException("Employee " + e.getName() + " is already assigned to " + shift + " shift.");
        }
        assigned.add(e);
        System.out.println("Assigned " + e.getName() + " to " + shift + " shift.");
    }

    public void displayAssignments() {
        for (ShiftTime shift : ShiftTime.values()) {
            System.out.println(shift + " shift: " + shiftAssignments.get(shift));
        }
    }
}
