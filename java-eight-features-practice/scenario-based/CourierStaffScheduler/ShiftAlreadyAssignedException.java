package CourierStaffScheduler;

public class ShiftAlreadyAssignedException extends Exception {
    public ShiftAlreadyAssignedException(String message) {
        super(message);
    }
}