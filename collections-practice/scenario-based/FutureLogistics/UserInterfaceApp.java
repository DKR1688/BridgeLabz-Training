package FutureLogistics;

import java.util.*;
public class UserInterfaceApp {
	public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Utility util =new Utility();
        List<GoodsTransport> records =new ArrayList<>();

        while (true) {
        	System.out.println();
            System.out.println("Future Logistics Billing System------");
            System.out.println("1. Enter Goods Transport Details");
            System.out.println("2. View All Records");
            System.out.println("3. Exit");
            System.out.print("Choose an option- ");

            int choice;
            try {
                choice =Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                	//Format for BrickTransport- RTS123A:2026-01-31:5:BrickTransport:10.5:400:15.0;
                    //Format for TimberTransport- RTS456B:2026-01-31:4:TimberTransport:20.0:5.0:Premium:30.0;
                    System.out.println("Enter the goods transport details- ");
                    String input =sc.nextLine();

                    String[] parts =input.split(":");
                    if (parts.length<7) {
                        System.out.println("Invalid input format. Please try again.");
                        continue;
                    }

                    String transportId =parts[0];
                    if (!util.validateTransportId(transportId)) {
                        continue; //skipping invalid record
                    }

                    GoodsTransport gt =util.parseDetails(input);
                    if (gt==null) {
                        System.out.println("Unknown transport type. Please try again.");
                        continue;
                    }

                    displayTransportDetails(gt, util);
                    records.add(gt); //adding records to later view
                
                case 2:
                    if (records.isEmpty()) {
                        System.out.println("No records available.");
                    } else {
                        System.out.println("\nAll transport records are---");
                        for (GoodsTransport gt1 : records) {
                            displayTransportDetails(gt1, util);
                        }
                    }
                
                case 3:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;
                
                default:
                	System.out.println("Invalid choice, please try again.");
            }
        }
    }

    //a method to display details of any transport record
    private static void displayTransportDetails(GoodsTransport gt, Utility utility) {
        String type =utility.findObjectType(gt);

        System.out.println("Transporter ID: " + gt.getTransportId());
        System.out.println("Date of transport: " + gt.getTransportDate());
        System.out.println("Transport rating: " + gt.getTransportRating());

        if (type.equals("BrickTransport")) {
            BrickTransport bt = (BrickTransport) gt;
            System.out.println("Quantity of bricks: " + bt.getBrickQuantity());
            System.out.println("Brick price: " + bt.getBrickPrice());
            System.out.println("Vehicle selected: " + bt.vehicleSelection());
            System.out.println("Total charge: " + bt.calculateTotalCharge());
        } else if (type.equals("TimberTransport")) {
            TimberTransport tt = (TimberTransport) gt;
            System.out.println("Timber type: " + tt.getTimberType());
            System.out.println("Timber price per kilo: " + tt.getTimberPrice());
            System.out.println("Vehicle selected: " + tt.vehicleSelection());
            System.out.println("Total charge: " + tt.calculateTotalCharge());
        }
        System.out.println();
    }
}
