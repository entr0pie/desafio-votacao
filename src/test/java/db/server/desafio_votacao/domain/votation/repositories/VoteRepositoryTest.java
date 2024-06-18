package db.server.desafio_votacao.domain.votation.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.repositories.AgendaRepository;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.repositories.UserRepository;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.repositories.VotingSessionRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class VoteRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VotingSessionRepository votingSessionRepository;

	@Autowired
	private AgendaRepository agendaRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Test
	@DisplayName("Should find by user id and session id")
	public void shouldFindByUserIdAndSessionId() {
		UserModel user = new UserModel(null, "email1", "cpf1");
		this.userRepository.save(user);

		AgendaModel agenda = new AgendaModel(null, "Title", "Description");
		this.agendaRepository.save(agenda);

		VotingSessionModel session = new VotingSessionModel(null, agenda, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(10));
		this.votingSessionRepository.save(session);

		VoteModel vote = new VoteModel(null, user, session, true);
		this.voteRepository.save(vote);

		VoteModel receivedVote = this.voteRepository.findByUserIdAndSessionId(user.getId(), session.getId()).get();
		assertEquals(vote.getId(), receivedVote.getId(), "Id must be the same.");
		assertEquals(vote.getUser().getId(), receivedVote.getUser().getId(), "User id must be the same.");
		assertEquals(vote.getSession().getId(), receivedVote.getSession().getId(), "Session id must be the same.");
		assertEquals(vote.getAgrees(), receivedVote.getAgrees(), "Agrees must be the same.");

		Optional<VoteModel> voteOptional = this.voteRepository.findByUserIdAndSessionId(-1, -1);
		assertEquals(voteOptional, Optional.empty(), "Vote must not be found.");
	}

	@Test
	@DisplayName("Should count by session id and agrees")
	public void shouldCountBySessionIdAndAgrees() {
		UserModel user = new UserModel(null, "email2", "cpf2");
		this.userRepository.save(user);

		AgendaModel agenda = new AgendaModel(null, "Title", "Description");
		this.agendaRepository.save(agenda);

		VotingSessionModel session = new VotingSessionModel(null, agenda, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(10));
		this.votingSessionRepository.save(session);

		VoteModel vote1 = new VoteModel(null, user, session, true);
		this.voteRepository.save(vote1);

		VoteModel vote2 = new VoteModel(null, user, session, false);
		this.voteRepository.save(vote2);

		Integer agrees = this.voteRepository.countBySessionIdAndAgrees(session.getId(), true);
		assertEquals(agrees, 1, "Agrees must be 1.");

		Integer disagrees = this.voteRepository.countBySessionIdAndAgrees(session.getId(), false);
		assertEquals(disagrees, 1, "Disagrees must be 1.");
	}

}
