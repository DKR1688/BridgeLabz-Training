package methods;

import java.util.*;
public class SpringSeason {
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter month and date ");
		int month =sc.nextInt();
		int day =sc.nextInt();
		
		//prints “Its a Spring Season” otherwise prints “Not a Spring Season”
		boolean springSeason = isSpringSeason(month,day);
		if(springSeason) {
			System.out.println("It is a spring season");
		}else {
			System.out.println("It is not a spring season");
		}
		sc.close();
		
	}
	
	//a Method to check for Spring season and return a boolean true or false 
	static boolean isSpringSeason(int month, int day) {
		if ((month==3 && day>=20 && day<=31) || 
	            (month==4 && day>=1 && day<=30) || 
	            (month==5 && day>=1 && day<=31) || 
	            (month==6 && day>=1 && day<=20)) {
	            return true;
	        }else{
	            return false;
	        }
	}
}
