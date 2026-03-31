package OnlineVotingSystem;

public class Candidate {
	private int candidateId;
	private String candidateName;
	private int voteCount = 0;
	
	public Candidate(int candidateId,String candidateName) {
		this.candidateId = candidateId;
		this.candidateName = candidateName;
	}
	
	public int getCandidateId() {
		return candidateId;
	}
	
	public String getCandidateName() {
		return candidateName;
	}
	
	public void addVote() {
		voteCount++;
	}
	
	public int getVote() {
		return voteCount;
	}
}
