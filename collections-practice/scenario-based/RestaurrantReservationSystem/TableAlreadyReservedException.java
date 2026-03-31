package RestaurrantReservationSystem;

class TableAlreadyReservedException extends Exception {
    public TableAlreadyReservedException(String message) {
        super(message);
    }
}
