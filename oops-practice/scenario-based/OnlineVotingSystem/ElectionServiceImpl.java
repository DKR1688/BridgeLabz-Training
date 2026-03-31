package OnlineVotingSystem;

public class ElectionServiceImpl extends ElectionService{
	private Voter voters[] = new Voter[5];
	private Candidate candidates[] = new Candidate[5];
	private Vote votes[] = new Vote[5];
	
	private int voterCount = 0;
    private int candidateCount = 0;
    private int voteCount = 0;
    
    @Override
    public void registerVoter(Voter voter) {
    	voters[voterCount++] = voter;
    	System.out.println("Voter Registered successfully!");
    }
    
    @Override
    public void addCandidate(Candidate candidate) {
    	candidates[candidateCount++] = candidate;
    	System.out.println("Candidate added successfully");
    }
    
    @Override
    public void castVote(int voterId,int candidateId) throws DuplicateVoteException{
    	Voter voter = null;
    	Candidate candidate = null;
    	for(int i = 0;i < voterCount;i++) {
    		if(voters[i].getId()==voterId) {
    			voter = voters[i];
    			break;
    		}
    	}
    	if(voter == null) {
    		return;
    	}
    	
    	if(voter.hasVoted()) {
    		throw new DuplicateVoteException("Voter has already voted");
    	}
    	
    	for(int i = 0;i < candidateCount;i++) {
    		if(candidates[i].getCandidateId()==candidateId) {
    			candidate = candidates[i];
    			break;
    		}
    	}
    	
    	if(candidate == null) {
    		return;
    	}
    	
    	voter.markVoted();
        candidate.addVote();
        votes[voteCount++] = new Vote(voterId, candidateId);

        System.out.println("Vote cast successfully");
    }
    
    public void declareResult() {
        System.out.println("\nElection Results:");
        for (int i = 0; i < candidateCount; i++) {
            System.out.println(candidates[i].getCandidateName() + " : " + candidates[i].getVote() + " votes");
        }
    }
    
}
