package db.server.desafio_votacao.domain.votation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
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

@SpringBootTest(classes = VoteServiceJpa.class)
@AutoConfigureTestDatabase
public class VoteServiceJpaTest {

	@MockBean
	private VoteRepository voteRepository;

	@MockBean
	private VotingSessionService votingSessionService;

	@MockBean
	private UserService userService;

	@Autowired
	private VoteServiceJpa voteServiceJpa;

	@Test
	@DisplayName("Should throw user not found exception")
	public void shouldThrowUserNotFoundException() {
		when(this.userService.findById(eq(1)))
				.thenThrow(new UserNotFoundException("The requested user was not found."));

		assertThrows(UserNotFoundException.class, () -> this.voteServiceJpa.vote(1, 1, true));
	}

	@Test
	@DisplayName("Should throw voting session not found exception")
	public void shouldThrowVotingSessionNotFoundException() {
		UserModel user = new UserModel(1, "email", "cpf");
		when(this.userService.findById(eq(1))).thenReturn(user);
		when(this.votingSessionService.findById(eq(2)))
				.thenThrow(new VotingSessionNotFoundException("The requested voting session was not found."));

		assertThrows(VotingSessionNotFoundException.class, () -> this.voteServiceJpa.vote(2, 1, true));
	}

	@Test
	@DisplayName("Should throw already vote exception")
	public void shoulThrowAlreadyVoteException() {
		UserModel user = new UserModel(1, "email", "cpf");
		VotingSessionModel session = new VotingSessionModel(2, new AgendaModel(1, "Important theme", "description"),
				LocalDateTime.now(), LocalDateTime.now());

		VoteModel vote = new VoteModel(1, user, session, false);

		when(this.userService.findById(eq(1))).thenReturn(user);
		when(this.votingSessionService.findById(eq(2))).thenReturn(session);
		when(this.voteRepository.findByUserIdAndSessionId(eq(1), eq(2))).thenReturn(Optional.of(vote));

		assertThrows(AlreadyVotedException.class, () -> this.voteServiceJpa.vote(2, 1, true));
	}

	@Test
	@DisplayName("Should throw session closed exception when before start date")
	public void shouldThrowSessionClosedExceptionWhenBeforeStartDate() {
		UserModel user = new UserModel(1, "email", "cpf");
		VotingSessionModel session = new VotingSessionModel(2, new AgendaModel(1, "Important theme", "description"),
				LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(20));

		when(this.userService.findById(eq(1))).thenReturn(user);
		when(this.votingSessionService.findById(eq(2))).thenReturn(session);

		assertThrows(SessionClosedException.class, () -> this.voteServiceJpa.vote(2, 1, true));
	}

	@Test
	@DisplayName("Should throw session closed exception when after end date")
	public void shouldThrowSessionClosedExceptionWhenAfterEndDate() {
		UserModel user = new UserModel(1, "email", "cpf");
		VotingSessionModel session = new VotingSessionModel(2, new AgendaModel(1, "Important theme", "description"),
				LocalDateTime.now().minusMinutes(20), LocalDateTime.now().minusMinutes(10));

		when(this.userService.findById(eq(1))).thenReturn(user);
		when(this.votingSessionService.findById(eq(2))).thenReturn(session);

		assertThrows(SessionClosedException.class, () -> this.voteServiceJpa.vote(2, 1, true));
	}

	@Test
	@DisplayName("Should vote")
	public void shouldVote() {
		UserModel user = new UserModel(1, "email", "cpf");
		VotingSessionModel session = new VotingSessionModel(2, new AgendaModel(1, "Important theme", "description"),
				LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10));

		when(this.userService.findById(eq(1))).thenReturn(user);
		when(this.votingSessionService.findById(eq(2))).thenReturn(session);
		when(this.voteRepository.findByUserIdAndSessionId(eq(1), eq(2))).thenReturn(Optional.empty());

		VoteModel vote = new VoteModel(1, user, session, false);
		when(this.voteRepository.save(eq(vote))).thenReturn(vote);

		this.voteServiceJpa.vote(2, 1, false);
	}

	@Test
	@DisplayName("Should throw SessionNotFoundException when get results")
	public void shouldThrowSessionNotFoundExceptionWhenGetResults() {
		when(this.votingSessionService.findById(eq(1)))
				.thenThrow(new VotingSessionNotFoundException("The requested voting session was not found."));

		assertThrows(VotingSessionNotFoundException.class, () -> this.voteServiceJpa.getResults(1));
	}

	@Test
	@DisplayName("Should get results")
	public void shouldGetResults() {
		VotingSessionModel session = new VotingSessionModel(1, new AgendaModel(1, "Important theme", "description"),
				LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusMinutes(10));

		when(this.votingSessionService.findById(eq(1))).thenReturn(session);
		when(this.voteRepository.countBySessionIdAndAgrees(eq(1), eq(true))).thenReturn(10);
		when(this.voteRepository.countBySessionIdAndAgrees(eq(1), eq(false))).thenReturn(5);

		VotationResults results = this.voteServiceJpa.getResults(1);

		assertEquals(10, results.getAgrees());
		assertEquals(5, results.getDisagrees());
		assertEquals(15, results.getTotalVotes());
	}

}
