package OnlineVotingSystem;

public class Voter {
	private int voterId;
	private String voterName;
	private boolean hasVoted = false;
	public Voter(int voterId,String voterName) {
		this.voterId = voterId;
		this.voterName = voterName;
	}
	
	public String getName() {
		return voterName;
	}
	
	public int getId() {
		return voterId;
	}
	
	public boolean hasVoted() {
		return hasVoted;
	}
	
	public void markVoted() {
        hasVoted = true;
    }
}
