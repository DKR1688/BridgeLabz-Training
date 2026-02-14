package SmartParkingSlotAllocationSystem;
import java.util.*;

public class ParkingManager {
    private Map<Integer, ParkingSlot> slots;
    private Queue<Vehicle> waitingQueue;

    public ParkingManager(int totalSlots) {
        slots = new HashMap<>();
        waitingQueue = new LinkedList<>();
        for (int i = 1; i <= totalSlots; i++) {
            slots.put(i, new ParkingSlot(i));
        }
    }

    public void allocateSlot(Vehicle v) throws NoParkingSlotAvailableException {
        for (int i = 1; i <= slots.size(); i++) {
            ParkingSlot slot = slots.get(i);
            if (slot.isFree()) {
                slot.assignVehicle(v);
                System.out.println("Allocated slot " + i + " to vehicle " + v.getNumber());
                return;
            }
        }
        // If no slot free, add to waiting queue
        waitingQueue.add(v);
        throw new NoParkingSlotAvailableException("No free slot! Vehicle " + v.getNumber() + " added to waiting queue.");
    }

    public void freeSlot(int id) {
        ParkingSlot slot = slots.get(id);
        if (!slot.isFree()) {
            slot.freeSlot();
            System.out.println("Slot " + id + " is now free.");
            // Allocate to waiting vehicle if any
            if (!waitingQueue.isEmpty()) {
                Vehicle next = waitingQueue.poll();
                try {
                    allocateSlot(next);
                } catch (NoParkingSlotAvailableException e) {
                    // This should not happen immediately after freeing
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void displaySlots() {
        for (ParkingSlot slot : slots.values()) {
            System.out.println(slot);
        }
    }
}