/*Sandeep’s Fitness Challenge Tracker ️‍♂️
Each day Sandeep completes a number of push-ups.
● Store counts for a week.
● Use for-each to calculate total and average.
● Use continue to skip rest days.*/

import java.util.*;
public class FitnessChallengeTracker {
	public static void main(String[] args) { 
		Scanner scanner=new Scanner(System.in);
		
        int[] pushUps = new int[7];
		for (int i=0; i<pushUps.length; i++) {
			System.out.println("Enter the no of push ups of Sandeep for Day "+i+1+" - ");
			pushUps[i] =scanner.nextInt();
		}

        int totalPushUps=0;
        int daysCounted=0;

        //Use for-each to calculate total.
        for (int count:pushUps) {
            if (count==0) {
                continue;
            }
            totalPushUps+=count;
            daysCounted++;
        }

        double average = (daysCounted>0) ? (double) totalPushUps/daysCounted : 0;

        System.out.println("Sandeep’s Fitness Challenge Tracker - ");
        System.out.println("Total push-ups - " + totalPushUps+"\n"+"Average push-ups per workout day - "+average);
        scanner.close();
    }
}
