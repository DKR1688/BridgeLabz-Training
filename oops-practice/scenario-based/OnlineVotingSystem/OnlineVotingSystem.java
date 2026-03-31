package OnlineVotingSystem;

public class OnlineVotingSystem {

	public static void main(String[] args) {
		
		ElectionService election = new ElectionServiceImpl();

        election.registerVoter(new Voter(1, "Deepak"));
        election.registerVoter(new Voter(2, "Rajput"));

        election.addCandidate(new Candidate(101, "Candidate A"));
        election.addCandidate(new Candidate(102, "Candidate B"));

        try {
            election.castVote(1, 101);
            election.castVote(2, 102);
            election.castVote(1, 102);//duplicate vote
        } catch (DuplicateVoteException e) {
            System.out.println(e.getMessage());
        }

        election.declareResult();

	}

}
