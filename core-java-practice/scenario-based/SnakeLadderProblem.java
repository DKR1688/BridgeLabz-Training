import java.util.*;
public class SnakeLadderProblem {
    public static void main(String[] args) {
        onePlayerGame();
        gameWithTwoPlayer();
    }
   
    static void onePlayerGame() {
    	Random random = new Random();
        System.out.println("Start snake & ladder game:");

        //UC1 - Snake and Ladder game played with single player at start position 0
        int position =0;
        System.out.println("Single player will start at position 0:");
        System.out.println("Start position is- " + position);
        System.out.println();

        //UC2 - The Player rolls the die to get a number between 1 to 6.
        int die =random.nextInt(6) + 1;
        System.out.println("Rolling the die to get 1 to 6- " + die);
        System.out.println();

        // UC3- The Player then checks for an Option. They are No Play, Ladder or Snake.
        int option =random.nextInt(3);
        String[] options = {"No Play", "Ladder", "Snake"};
        int prePosition = position;
        if (option==0) {
            // No Play
        } else if (option==1) {
            position+=die;
        } else {
            position-=die;
        }
        System.out.println("Die number is- " + die + ", Picked option is- " + options[option] +
                ", Old position is- " + prePosition + " and Current position after move is- " + position);
        System.out.println();

        //UC4 - Repeat till the Player reaches the winning position 100.
        int moves=0;
        while (position<100) {
           die = random.nextInt(6) + 1;
           option = random.nextInt(3);
           prePosition = position;

            if (option==0) {
                // No Play
            } else if (option==1) {
                position+=die;
            } else {
                position-=die;
                if (position<0) {
                    position = 0;
                }
            }
            moves++;
            System.out.println("Move is- " + moves + ", Die number is- " + die + ", Picked option is- " + options[option] +
            		", Old position is- " + prePosition +" and Current position is- " + position);
        }
        System.out.println("Player has reached 100th position.");
        System.out.println();
        
        //UC5 - Ensure the player gets to exact winning position 100.
        //UC6 - Report the number of times the dice was played to win the game and also the position after every die role
        while (position!=100) {
            die = random.nextInt(6)+1;
            option = random.nextInt(3);
            prePosition = position;
            int attempted = position;

            if (option==0) {
                //No play
            } else if (option==1) {
                attempted = position+die;
                if (attempted==100) {
                    position=100;
                } else if (attempted>100) {
                    //stay at old position
                } else {
                    position=attempted;
                }
            } else {
                attempted = position-die;
                position = Math.max(0, attempted);// restart at 0 if below 0
            }
            moves++;
            System.out.println("Move is- " + moves + ", Die number is- " + die + ", Picked option is- " + options[option] +
            		", Old position is- " + prePosition +", Attempts to reach 100th is- "+attempted+" and Current position is- " + position);
        }
        System.out.println("Player has reached exactly 100 in "+ moves+" moves or dice counts");
        System.out.println();
   	}
    
    //UC7 - Play the game with 2 Player. In this case if a Player gets a Ladder
	// then plays again.Finally report which Player won the game
    static void gameWithTwoPlayer() {
    	Random random =new Random();
    	int player1 =0;
        int player2 =0;
        int rolls1 =0;
        int rolls2 =0;
        boolean turn1 =true;
        
        String[] options = {"No Play", "Ladder", "Snake"};
        while(player1!=100 && player2!=100) {
        	int die =random.nextInt(6)+1;
            int option =random.nextInt(3);

            if (turn1) {
                rolls1++;
                int old =player1;
                int attempted =player1;

                if (option==0) {
                    // stay
                } else if (option==1) {
                    attempted = player1 + die;
                    if (attempted == 100) {
                        player1 = 100;
                    } else if (attempted > 100) {
                        // stay
                    } else {
                        player1 = attempted;
                    }
                } else {
                    attempted = player1 - die;
                    player1= Math.max(0, attempted);
                }
                System.out.println("Player1 | Die=" + die+ " | Option=" + options[option] + " | Old=" + old 
                	    + " | Attempted=" + attempted + " | New=" + player1);

                if (player1 == 100) {
                	break;
                }
                if (option != 1) {
                	turn1= false; // extra turn only on ladder
                } 
            }else {
                rolls2++;
                int old = player2;
                int attempted = player2;

                if (option == 0) {
                    // stay
                } else if (option == 1) { // Ladder
                    attempted = player2 + die;
                    if (attempted == 100) {
                        player2 = 100;
                    } else if (attempted > 100) {
                        // stay
                    } else {
                        player2= attempted;
                    }
                } else { // Snake
                    attempted = player2 - die;
                    player2 = Math.max(0, attempted);
                }
                System.out.println("Player2 | Die=" + die+ " | Option=" + options[option] 
                	    + " | Old=" + old + " | Attempted=" + attempted+ " | New=" + player2);

                if (player2 == 100) {
                	break;
                }
                if (option != 1) {
                	turn1 = true; // extra turn only on ladder
                }
            }
        }
        System.out.println();
        if (player1 == 100) {
        	System.out.printf("Player1 won because he has reached 100 in %d rolls.%n%n", rolls1);
        } else {
        	System.out.printf("Player2 won because he has reached 100 in %d rolls.%n%n", rolls2);
        }
   }
}