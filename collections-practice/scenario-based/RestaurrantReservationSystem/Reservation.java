package RestaurrantReservationSystem;

class Reservation {
    String customerName;
    Table table;
    String timeSlot;

    Reservation(String customerName, Table table, String timeSlot) {
        this.customerName =customerName;
        this.table = table;
        this.timeSlot =timeSlot;
    }

    public Table getTable() {
        return table;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return "Reservation{" + "customer='" + customerName + '\'' +
                ", table=" + table.getTableNumber() +
                ", timeSlot='" + timeSlot + '\'' + '}';
    }
}