package methods;

import java.util.*;
public class OtpGenerator {
	 public static void main(String[] args) {
		 Scanner sc=new Scanner(System.in);
		 System.out.println("Enter the number of otps ");
		 int n=sc.nextInt();
		 
	     int[] otp = new int[n];
	     for (int i=0; i<n; i++) {
	        	otp[i] = generateOTP();
	     }
	     System.out.println("OTPs are " +java.util.Arrays.toString(otp));
	     System.out.println("All digit are unique or not - " +unique(otp));
	     sc.close();
	    }
	 
	 //a method to generate a 6-digit OTP number using Math.random() 
	 static int generateOTP() {
	     return (int)(Math.random() * 900000) + 100000;
	 }
	 
	 // a method to ensure that the OTP numbers generated are unique. If unique return true else return false
	 static boolean unique(int[] arr) {
	     java.util.HashSet<Integer> set = new java.util.HashSet<>();
	     for (int x : arr) {
	    	 set.add(x);
	     }
	      return set.size()== arr.length;
	 }
}
