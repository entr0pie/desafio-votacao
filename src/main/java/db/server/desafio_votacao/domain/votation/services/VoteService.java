package db.server.desafio_votacao.domain.votation.services;

import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.votation.dtos.VotationResults;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;

/**
 * Service for vote operations.
 * 
 * @author Caio Porcel
 */
public interface VoteService {
	/**
	 * Vote for a session.
	 * 
	 * @param session session to vote in.
	 * @param user    user that is voting.
	 * @param agrees  true if the user agrees with the voting session, false
	 *                otherwise.
	 * 
	 * @return the vote that was created.
	 * @throws AlreadyVotedException          if the user has already voted in the
	 *                                        session.
	 * @throws SessionClosedException         if the session is closed.
	 * @throws VotingSessionNotFoundException if the session is not found.
	 * @throws UserNotFoundException          if the user is not found.
	 */
	VoteModel vote(Integer sessionId, Integer userId, Boolean agrees)
			throws AlreadyVotedException, SessionClosedException, VotingSessionNotFoundException, UserNotFoundException;

	/**
	 * Get the results of a session.
	 * 
	 * @param sessionId session to get the results from.
	 * @return the results
	 * @throws VotingSessionNotFoundException if the session is not found.
	 */
	VotationResults getResults(Integer sessionId) throws VotingSessionNotFoundException;

}
