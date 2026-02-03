package FactoryRobotHazardAnalyzer;

import java.util.*;
public class Program {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        RobotHazardAuditor auditor=new RobotHazardAuditor();

        try {
            System.out.println("Enter arm precision (0.0-1.0)- ");
            double armPrecision =Double.parseDouble(sc.nextLine());

            System.out.println("Enter worker density (1-20)- ");
            int workerDensity =Integer.parseInt(sc.nextLine());

            System.out.println("Enter machinery state (Worn/Faulty/Critical)- ");
            String machineryState =sc.nextLine();

            double riskScore =auditor.calculateHazardRisk(armPrecision, workerDensity, machineryState);
            System.out.println("Robot hazard risk score- "+riskScore);

        } catch (RobotSafetyException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid input format");
        }

        sc.close();
    }
}