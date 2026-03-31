package ParcelTracker;

import java.util.*;
public class ParcelTrack {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
        Tracker tracker = new Tracker();
        int choice;

        do {
        	System.out.println("Parcel Tracker Menu--- ");
            System.out.println("1- Track Parcel");
            System.out.println("2- Add Checkpoint");
            System.out.println("3- Mark Parcel Lost");
            System.out.println("4- Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
            case 1:
                tracker.trackParcel();
                break;
            case 2:
                System.out.print("Enter stage after which to add checkpoint: ");
                String afterStage = sc.nextLine();
                System.out.print("Enter new checkpoint name: ");
                String newStage = sc.nextLine();
                tracker.addCheckpoint(afterStage, newStage);
                break;
            case 3:
                System.out.print("Enter stage name after which parcel is lost: ");
                String stageName = sc.nextLine();
                tracker.markLost(stageName);
                break;
            case 4:
                System.out.println("Exiting");
                break;
            default:
                System.out.println("Invalid choice.");
            }
       }
        while (choice!=4);
        sc.close();
	}
}
