package OnlineVotingSystem;

public abstract class ElectionService {
	abstract void registerVoter(Voter voter);
    abstract void addCandidate(Candidate candidate);
    abstract void castVote(int voterId, int candidateId)throws DuplicateVoteException;
    abstract void declareResult();
}
