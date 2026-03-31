package RestaurrantReservationSystem;

class Table {
    int tableNumber;
    int capacity;

    Table(int tableNumber, int capacity) {
        this.tableNumber =tableNumber;
        this.capacity = capacity;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Table{" + "tableNumber=" + tableNumber + ", capacity=" + capacity + '}';
    }
}