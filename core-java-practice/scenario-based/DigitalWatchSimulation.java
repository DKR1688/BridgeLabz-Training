/*Digital Watch Simulation ⏱️
Simulate a 24-hour watch:
● Print hours and minutes in a nested for-loop.
● Use a break to stop at 13:00 manually (simulate power cut).*/

import java.util.*;
public class DigitalWatchSimulation {
	public static void main(String[] args) {
		
        //this outer loop for hours from 0 to 23.
        for (int hour=0; hour<24; hour++) {
        	
            //this inner loop for minutes from 0 to 59.
            for (int minute=0; minute<60; minute++) {
            	
                //here we are printing current time in HH:MM format
            	System.out.println(String.format("%02d:%02d", hour, minute));

                // Simulating power cut at 13:00
                if (hour==13 && minute==0) {
                    System.out.println("Power cut at 13:00.");
                    break;
                }
            }
            if (hour==13) {
                break;
            }
        }
    }
}
