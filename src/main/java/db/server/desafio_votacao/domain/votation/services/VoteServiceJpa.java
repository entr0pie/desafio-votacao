package db.server.desafio_votacao.domain.votation.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.services.UserService;
import db.server.desafio_votacao.domain.votation.dtos.VotationResults;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import db.server.desafio_votacao.domain.votation.repositories.VoteRepository;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.services.VotingSessionService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteServiceJpa implements VoteService {

	private final VoteRepository voteRepository;
	private final VotingSessionService votingSessionService;
	private final UserService userService;

	@Override
	public VoteModel vote(Integer sessionId, Integer userId, Boolean agrees) throws AlreadyVotedException,
			SessionClosedException, VotingSessionNotFoundException, UserNotFoundException {

		UserModel user = userService.findById(userId);
		VotingSessionModel session = votingSessionService.findById(sessionId);

		if (this.voteRepository.findByUserIdAndSessionId(userId, sessionId).isPresent()) {
			throw new AlreadyVotedException();
		}

		if (isSessionOpened(session)) {
			VoteModel vote = new VoteModel();
			vote.setUser(user);
			vote.setSession(session);
			vote.setAgrees(agrees);
			return voteRepository.save(vote);
		}

		throw new SessionClosedException();
	}

	private boolean isSessionOpened(VotingSessionModel session) throws VotingSessionNotFoundException {
		LocalDateTime now = LocalDateTime.now();
		boolean isAfterStart = now.isAfter(session.getStartDate());
		boolean isBeforeEnd = now.isBefore(session.getEndDate());
		return isAfterStart && isBeforeEnd;
	}

	@Override
	public VotationResults getResults(Integer sessionId) throws VotingSessionNotFoundException {
		VotingSessionModel session = votingSessionService.findById(sessionId);

		Integer agrees = voteRepository.countBySessionIdAndAgrees(sessionId, true);
		Integer disagrees = voteRepository.countBySessionIdAndAgrees(sessionId, false);
		Integer totalVotes = agrees + disagrees;

		return new VotationResults(session, agrees, disagrees, totalVotes);
	}

}
