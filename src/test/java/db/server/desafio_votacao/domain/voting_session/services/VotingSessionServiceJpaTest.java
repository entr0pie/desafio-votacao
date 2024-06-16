package db.server.desafio_votacao.domain.voting_session.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.services.AgendaService;
import db.server.desafio_votacao.domain.voting_session.exceptions.InvalidDateException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.repositories.VotingSessionRepository;

@SpringBootTest(classes = { VotingSessionServiceJpa.class, VotingSessionService.class })
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class VotingSessionServiceJpaTest {

	@MockBean
	private VotingSessionRepository repository;

	@MockBean
	private AgendaService agendaService;

	@Autowired
	private VotingSessionServiceJpa service;

	@Test
	@DisplayName("Should create start date when null")
	public void shouldCreateStartDate_whenNull() {
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(60);
		AgendaModel mockedAgenda = new AgendaModel(1, "title", "description");

		when(this.agendaService.findById(eq(1))).thenReturn(mockedAgenda);
		when(this.repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		LocalDateTime expectedStartDate = LocalDateTime.now();
		VotingSessionModel votingSession = this.service.create(1, null, endDate);

		assertTrue(
				expectedStartDate.isBefore(votingSession.getStartDate())
						|| expectedStartDate.isEqual(votingSession.getStartDate()),
				"The start date should be the current date.");

	}

	@Test
	@DisplayName("Should create end date when null")
	public void shouldInferEndDate_whenNull() {
		LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
		AgendaModel mockedAgenda = new AgendaModel(1, "title", "description");

		when(this.agendaService.findById(eq(1))).thenReturn(mockedAgenda);
		when(this.repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		LocalDateTime expectedEndDate = LocalDateTime.now().plusMinutes(1);
		VotingSessionModel votingSession = this.service.create(1, startDate, null);

		assertTrue(
				expectedEndDate.isBefore(votingSession.getEndDate())
						|| expectedEndDate.isEqual(votingSession.getEndDate()),
				"The end date should be the current date plus 1 minute.");
	}

	@Test
	@DisplayName("Should throw exception when end is before start")
	public void shouldThrowException_whenEndIsBeforeStart() {
		LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
		LocalDateTime endDate = LocalDateTime.now().minusMinutes(1);
		AgendaModel mockedAgenda = new AgendaModel(1, "title", "description");

		when(this.agendaService.findById(eq(1))).thenReturn(mockedAgenda);

		assertThrows(InvalidDateException.class, () -> {
			this.service.create(1, startDate, endDate);
		});
	}

	@Test
	@DisplayName("Should throw exception when start is before now")
	public void shouldThrowException_whenStartIsBeforeNow() {
		LocalDateTime startDate = LocalDateTime.now().minusMinutes(1);
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(1);
		AgendaModel mockedAgenda = new AgendaModel(1, "title", "description");

		when(this.agendaService.findById(eq(1))).thenReturn(mockedAgenda);

		assertThrows(InvalidDateException.class, () -> {
			this.service.create(1, startDate, endDate);
		});
	}

	@Test
	@DisplayName("Should throw exception when agenda is not found")
	public void shouldThrowException_whenAgendaIsNotFound() {
		LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(2);

		when(this.agendaService.findById(eq(1))).thenThrow(new AgendaNotFoundException("Agenda not found"));

		assertThrows(RuntimeException.class, () -> {
			this.service.create(1, startDate, endDate);
		});
	}

	@Test
	@DisplayName("Should create voting session")
	public void shouldCreateVotingSession() {
		LocalDateTime startDate = LocalDateTime.now().plusMinutes(1);
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(2);
		AgendaModel mockedAgenda = new AgendaModel(1, "title", "description");

		when(this.agendaService.findById(eq(1))).thenReturn(mockedAgenda);
		when(this.repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		VotingSessionModel votingSession = this.service.create(1, startDate, endDate);

		assertTrue(votingSession.getAgenda().equals(mockedAgenda), "The agenda should be the same.");
		assertTrue(votingSession.getStartDate().equals(startDate), "The start date should be the same.");
		assertTrue(votingSession.getEndDate().equals(endDate), "The end date should be the same.");
	}

	@Test
	@DisplayName("Should find voting session by id")
	public void shouldFindVotingSessionById() {
		VotingSessionModel mockedVotingSession = new VotingSessionModel(1, new AgendaModel(1, "title", "description"),
				LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

		when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.of(mockedVotingSession));

		VotingSessionModel votingSession = this.service.findById(1);

		assertTrue(votingSession.equals(mockedVotingSession), "The voting session should be the same.");
	}

	@Test
	@DisplayName("Should throw exception when voting session is not found")
	public void shouldThrowException_whenVotingSessionIsNotFound() {
		when(this.repository.findById(eq(1))).thenReturn(java.util.Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			this.service.findById(1);
		});
	}

	@Test
	@DisplayName("Should find all voting sessions")
	public void shouldFindAllVotingSessions() {
		VotingSessionModel mockedVotingSession = new VotingSessionModel(1, new AgendaModel(1, "title", "description"),
				LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));

		Pageable pageable = PageRequest.of(0, 10);

		when(this.repository.findAll(eq(pageable))).thenReturn(new PageImpl<>(List.of(mockedVotingSession)));

		Page<VotingSessionModel> votingSessions = this.service.findAll(0, 10);

		assertTrue(votingSessions.getContent().size() == 1, "The number of voting sessions should be 1.");
		assertTrue(votingSessions.getContent().get(0).equals(mockedVotingSession),
				"The voting session should be the same.");

	}

}
