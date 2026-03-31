/*Election Booth Manager ️
Design a polling booth system.
● Take age input.
● Use if to check if eligible (>=18).
● Record vote (1, 2, or 3 for candidates).
● Loop for multiple voters, exit on special code.*/

import java.util.*;
public class PollingBoothSystem {

	public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        //Record vote (1, 2, or 3 for candidates).
	        int voteToA = 0;
	        int voteToB = 0;
	        int voteToC = 0;

	        //Loop for multiple voters, exit on special code
	        while (true) {
	            System.out.println("Enter age to vote or -1 to stop ");
	            int age =scanner.nextInt();
	            if (age==-1) {
	                break;
	            }
	            
	            // here we will check to person can vote or not
	            if (age>=18) {
	                System.out.println("You are eligible to vote");
	                System.out.print("Vote at 1-A or 2-B or 3-C ");
	                int vote = scanner.nextInt();

	                if (vote==1) {
	                    voteToA++;
	                } else if (vote==2) {
	                    voteToB++;
	                } else if (vote==3) {
	                    voteToC++;
	                } else {
	                    System.out.println("vote is invalid");
	                }
	            } else {
	                System.out.println("you are not eligible to votr");
	            }
	        }

	        System.out.println("Voting Result: ");
	        System.out.println("Candidate A have "+voteToA+" votes");
	        System.out.println("Candidate B have "+voteToB+" votes");
	        System.out.println("Candidate C have " +voteToC+" votes");
	        scanner.close();
	}

}
