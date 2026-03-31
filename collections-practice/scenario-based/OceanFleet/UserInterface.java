package OceanFleet;
import java.util.*;

public class UserInterface {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        VesselUtil util=new VesselUtil();

        System.out.println("Enter the number of vessels to be added-");
        int num=sc.nextInt();
        sc.nextLine();

        System.out.println("Enter vessel details");
        for (int i=0; i<num; i++) {
        	String in=sc.nextLine();
            String[] splited=in.split(":");
            Vessel v= new Vessel(splited[0], splited[1], Double.parseDouble(splited[2]), splited[3]);
            util.addVesselPerformance(v); 
        }

        System.out.println("Enter the Vessel Id to check speed-");
        String id=sc.nextLine();
        Vessel found =util.getVesselById(id);

        if (found!=null) {
        	System.out.println(found.getVesselId()+" | "+found.getVesselName()+" | "+ found.getVesselType()+" | "+found.getAverageSpeed()+" knots");
        } else {
            System.out.println("Vessel Id "+id+" not found");
        }

        System.out.println("High performance vessels are- ");
        for (Vessel v: util.getHighPerformanceVessels()) {
        	System.out.println(v.getVesselId()+" | " + v.getVesselName() +" | "+ v.getVesselType()+" | "+v.getAverageSpeed()+ " knots");
        }
        sc.close();
    }
}