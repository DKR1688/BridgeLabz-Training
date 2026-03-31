package RestaurrantReservationSystem;

import java.util.*;
class Restaurant {
    Map<Integer, Table> tables = new HashMap<>();
    List<Reservation> reservations = new ArrayList<>();

    //adding tables to restaurant
    public void addTable(Table table) {
        tables.put(table.getTableNumber(), table);
    }

    //reserving a table
    public void reserveTable(String customerName, int tableNumber, String timeSlot)
            throws TableAlreadyReservedException {
        Table table = tables.get(tableNumber);

        if (table==null) {
            System.out.println("Table " + tableNumber + " does not exist.");
            return;
        }

        for (Reservation r : reservations) {
            if (r.getTable().getTableNumber() == tableNumber &&
                r.getTimeSlot().equals(timeSlot)) {
                throw new TableAlreadyReservedException("Table " + tableNumber+" is already reserved for " + timeSlot);
            }
        }

        Reservation reservation =new Reservation(customerName, table, timeSlot);
        reservations.add(reservation);
        System.out.println("Reservation successful- "+reservation);
    }

    //canceling reservation
    public void cancelReservation(int tableNumber, String timeSlot) {
        reservations.removeIf(r -> r.getTable().getTableNumber()==tableNumber && r.getTimeSlot().equals(timeSlot));
        System.out.println("Reservation cancelled for table " +tableNumber + " at "+timeSlot);
    }

    //available tables for a given time slot
    public void showAvailableTables(String timeSlot) {
        System.out.println("Available tables for " + timeSlot + "- ");
        for (Table table : tables.values()) {
            boolean reserved = false;
            for (Reservation r : reservations) {
                if (r.getTable().getTableNumber()==table.getTableNumber() &&
                    r.getTimeSlot().equals(timeSlot)) {
                    reserved = true;
                    break;
                }
            }
            if (!reserved) {
                System.out.println(table);
            }
        }
    }
}