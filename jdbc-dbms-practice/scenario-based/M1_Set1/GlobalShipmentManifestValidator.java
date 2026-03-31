package M1_Set1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class GlobalShipmentManifestValidator {
	public boolean validateCode(String code) {
		if(!code.matches("^SHIP-[1-9][0-9]{5}$")) {
			return false;
		}
		String digits=code.substring(5);
		int count=1;
		for(int i=1; i<digits.length(); i++) {
			if(digits.charAt(i)==digits.charAt(i-1)) {
				count++;
				if(count>3) {
					return false;
				}
			}else {
				count=1;
			}
		}
		return true;
	}
	
	public boolean validateDate(String date) {
		if(!date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
			return false;
		}
		String[] parts=date.split("-");
		int year=Integer.parseInt(parts[0]);
		if(year<2000 || year>2099) {
			return false;
		}
		int month=Integer.parseInt(parts[1]);
		int day=Integer.parseInt(parts[2]);
		
		int[] daysInMonth= {31, (isLeapYear(year)?29:28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if(month<1 || month>12) {
			return false;
		}
		if(day<1||day>daysInMonth[month-1]) {
			return false;
		}
		return true;
	}
	
	private boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }
	
	public boolean validateMode(String mode) {
		if(mode.equals("AIR")||mode.equals("SEA")||mode.equals("ROAD")||mode.equals("RAIL")||mode.equals("EXPRESS")||mode.equals("FREIGHT")) {
			return true;
		}
		return false;
	}
	
	public boolean validateWeight(String weight) {
		if(!weight.matches("\\d+(\\.\\d{1,2})?")) {
			return false;
		}
		if(weight.matches("0\\d+.*")) {
			return false;
		}
		
		double value=Double.parseDouble(weight);
		if(value<=0||value>999999.99) {
			return false;
		}
		return true;
	}
	
	public boolean validateStatus(String status) {
		if(status.equals("DELIVERED")||status.equals("CANCELLED")||status.equals("IN_TRANSIT")) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int N=sc.nextInt();
		sc.nextLine();
		List<String> inputs=new ArrayList<>();
		for(int i=0; i<N; i++) {
			inputs.add(sc.nextLine());
		}
		for(int i=0; i<N; i++) {
		    String line = inputs.get(i);
		    String[] parts = line.split("\\|");
		    if (parts.length != 5) {
		        System.out.println("NON-COMPLIANT RECORD");
		        continue;
		    }

		    String code = parts[0];
		    String date = parts[1];
		    String mode = parts[2];
		    String weight = parts[3];   // keep as String
		    String status = parts[4];

		    GlobalShipmentManifestValidator g = new GlobalShipmentManifestValidator();
		    if (g.validateCode(code) &&
		        g.validateDate(date) &&
		        g.validateMode(mode) &&
		        g.validateWeight(weight) &&
		        g.validateStatus(status)) {
		        System.out.println("COMPLIANT RECORD");
		    } else {
		        System.out.println("NON-COMPLIANT RECORD");
		    }
		}
		
//		List<String> inputs=new ArrayList<>();
//		int N=sc.nextInt();
//		sc.nextLine();
//		for(int i=0; i<N; i++) {
//			inputs.add(sc.nextLine());
//		}
//		
//		for(int i=0; i<N; i++) {
//			String line=inputs.get(i);
//			String[] parts=line.split("\\|");
//			String code=parts[0];
//			String date=parts[1];
//			String mode=parts[2];
//			String weight=parts[3];
//			String status=parts[4];
//			
//			GlobalShipmentManifestValidator g=new GlobalShipmentManifestValidator();
//			if(g.validateCode(code)&&
//			g.validateDate(date)&&
//			g.validateMode(mode)&&
//			g.validateStatus(status)&&
//			g.validateWeight(weight)) {
//				System.out.println("COMPLIANT RECORD");
//			}
//			System.out.println("NON-COMPLIANT RECORD");
//		}
	}
}
