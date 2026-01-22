import java.util.*;
public class VotingSystem {
	public static void main(String[] args) {
        Map<String, Integer> votes=new HashMap<>();
        Map<String, Integer> orderedVotes=new LinkedHashMap<>();
        
        //votes in HashMap
        votes.put("Deepak", 9);
        votes.put("Abhay", 4);
        votes.put("Rajput", 8);
        System.out.println("Votes in hashMap- ");
        System.out.println(votes);


        Map<String, Integer> sortedVotes=new TreeMap<>(votes);
        //treeMap is used to display the results in sorted order
        System.out.println("\nVotes in treeMap- ");
        System.out.println(sortedVotes);

        //linkedHashMap is used to maintain the order of votes
        orderedVotes.put("Deepak", 3);
        orderedVotes.put("Rajput", 5);
        orderedVotes.put("Abahy", 2);
        System.out.println("\nVotes in linkedHashMap- ");
        System.out.println(orderedVotes);
    }
}
