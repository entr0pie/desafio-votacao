package db.server.desafio_votacao.domain.votation.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import db.server.desafio_votacao.domain.votation.models.VoteModel;

/**
 * Repository for the votes.
 */
public interface VoteRepository extends CrudRepository<VoteModel, Integer> {
	/**
	 * Find a vote by the user id and session id.
	 * 
	 * @param userId    the user id.
	 * @param sessionId the session id.
	 * @return the vote.
	 */
	Optional<VoteModel> findByUserIdAndSessionId(Integer userId, Integer sessionId);

	/**
	 * Count the number of votes in a session that agree or disagree.
	 * 
	 * @param sessionId the session id.
	 * @param agrees    if the votes should agree or disagree.
	 * @return the number of votes.
	 */
	Integer countBySessionIdAndAgrees(Integer sessionId, Boolean agrees);
}
