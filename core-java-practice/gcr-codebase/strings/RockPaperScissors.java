package strings;

import java.util.*;

public class RockPaperScissors {
	
	//Create a Method to find the Computer Choice using the Math.random
	static String computerChoice() {
        int c = (int)(Math.random()*3);
        if (c==0) return "rock";
        if (c==1) return "paper";
        return "scissors";
    }

	//Create a Method to find the winner between the user and the computer
    static String winner(String player, String comp) {
        if (player.equals(comp)) return "Draw";
        if ((player.equals("rock") && comp.equals("scissors")) ||
            (player.equals("scissors") && comp.equals("paper")) ||
            (player.equals("paper") && comp.equals("rock"))) return "Player";
        return "Computer";
    }

    //Create a Method to find the average and percentage of wins for the user 
    //and the computer and return a String 2D array
    static String[][] gameStats(String[] results) {
        int pWins = 0, cWins = 0, draws = 0;
        for (String r : results) {
            if (r.equals("Player")) pWins++;
            else if (r.equals("Computer")) cWins++;
            else draws++;
        }
        int total = results.length;
        String[][] table = new String[4][3];
        table[0]= new String[]{"Player Wins", String.valueOf(pWins), String.valueOf(pWins*100/total) + "%"};
        table[1]= new String[]{"Computer Wins", String.valueOf(cWins), String.valueOf(cWins*100/total) + "%"};
        table[2]= new String[]{"Draws", String.valueOf(draws), String.valueOf(draws*100/total) + "%"};
        table[3]= new String[]{"Total Games", String.valueOf(total), "100%"};
        return table;
    }

    //Create a Method to display the results of every game and also display 
    //the average and percentage wins 
    static void displayResults(String[] results, String[][] stats) {
        System.out.println("Game\tWinner");
        for (int i=0; i<results.length; i++)
            System.out.println((i+1) + "\t" + results[i]);

        System.out.println("\nSummary:");
        System.out.println("Type\tCount\tPercentage");
        for (int i = 0; i < stats.length; i++)
            System.out.println(stats[i][0] + "\t" + stats[i][1] + "\t" + stats[i][2]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of games: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        String[] results = new String[n];

        for (int i=0; i<n; i++) {
            System.out.print("Game " + (i+1) + " - Enter rock/paper/scissors: ");
            String player = scanner.nextLine().toLowerCase();
            String comp = computerChoice();
            results[i] = winner(player, comp);
            System.out.println("Computer chose: " +comp+ ", Winner: " +results[i]);
        }

        displayResults(results, gameStats(results));
        scanner.close();
    }
}