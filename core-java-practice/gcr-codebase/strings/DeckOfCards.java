package strings;


import java.util.Scanner;

public class DeckOfCards {

	//a deck of cards with suits "Hearts", "Diamonds", "Clubs", "Spades" and ranks from "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", and "Ace"
	static String[] suits ={"Hearts", "Diamonds", "Clubs", "Spades"};
	static String[] ranks ={"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};

	//a Method to Initialize the deck of cards with suits and ranks and return the deck. 
	//The deck is an array of strings where each string represents a card in the deck represented as "rank of suit" e.g., "2 of Hearts"
	public static String[] initializeDeck() {
      String[] deck= new String[suits.length * ranks.length];
      int k=0;
      for (String suit :suits) {
          for (String rank : ranks) {
              deck[k++] = rank+" of "+suit;
          }
      }
      return deck;
  }

	//a Method to Shuffle the deck of cards and return the shuffled deck. To shuffle the card iterate over 
	//the deck and swap each card with a random card from the remaining deck to shuffle the deck. 
	public static void shuffleDeck(String[] deck) {
      int n = deck.length;
      
      //nside the Loop Generate a random card number between i and n
      for (int i=0; i<n; i++) {
          int r = i+(int)(Math.random()*(n - i));
          String temp = deck[i];
          deck[i] = deck[r];
          deck[r] = temp;
      }
  }

	//a Method to distribute the deck of n cards to x number of players and return the players. 
	//For this Check the n cards can be distributed to x players. If possible then Create a 2D array 
	//to store the players and their cards
	public static String[][] distributeCards(String[] deck, int players, int cardsPerPlayer) {
      if (players *cardsPerPlayer > deck.length) {
    	  return null;
      }

      String[][] playerCards = new String[players][cardsPerPlayer];
      int k = 0;
      for (int i=0; i<players; i++) {
          for (int j=0; j<cardsPerPlayer; j++) {
              playerCards[i][j] =deck[k++];
          }
      }
      return playerCards;
  }

	//a Method to Print the players and their cards
	public static void displayPlayers(String[][] playerCards) {
      for (int i=0; i<playerCards.length; i++) {
          System.out.println("Player " + (i+1) + ":");
          for (String card : playerCards[i]) {
        	  System.out.print(card + ", ");
          }
      }
  }

  public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      String[] deck = initializeDeck();
      shuffleDeck(deck);

      int players = scanner.nextInt();
      System.out.println("Enter number of cards per player ");
      int cardsPerPlayer = scanner.nextInt();

      String[][] playerCards =distributeCards(deck, players, cardsPerPlayer);
      if (playerCards !=null) displayPlayers(playerCards);
      else System.out.println("Not enough cards to distribute ");

      scanner.close();
  }
}
