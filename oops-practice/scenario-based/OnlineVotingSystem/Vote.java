package OnlineVotingSystem;

public class Vote {
	private int voterId;
    private int candidateId;

    public Vote(int voterId, int candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }
}
