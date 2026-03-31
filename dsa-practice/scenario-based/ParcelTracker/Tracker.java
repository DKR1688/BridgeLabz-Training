package ParcelTracker;

public class Tracker {
	Stage head;

    Tracker() {
        head =new Stage("Packed");
        Stage shipped =new Stage("Shipped");
        Stage inTransit =new Stage("In Transit");
        Stage delivered =new Stage("Delivered");

        head.next = shipped;
        shipped.next = inTransit;
        inTransit.next = delivered;
    }

    //add a custom checkpoint after a given stage
    public void addCheckpoint(String afterStage, String newStage) {
        Stage current = head;
        while (current != null) {
            if (current.name.equals(afterStage)) {
                Stage checkpoint = new Stage(newStage);
                checkpoint.next = current.next;
                current.next = checkpoint;
                System.out.println("Checkpoint " +newStage+" added after " +afterStage);
                return;
            }
            current = current.next;
        }
        System.out.println("Stage " +afterStage+ " not found!");
    }

    //tracking parcel through stages
    public void trackParcel() {
        Stage current = head;
        while (current != null) {
            System.out.println("Parcel at stage- " +current.name);
            current = current.next;
        }
    }

    //managing missing parcel
    public void markLost(String stageName) {
        Stage current = head;
        while (current !=null) {
            if (current.name.equals(stageName)) {
                current.next =null; //Parcel lost after this stage
                System.out.println("Parcel lost after stage- " +stageName);
                return;
            }
            current = current.next;
        }
        System.out.println("Stage "+stageName+ " not found.");
    }
}
