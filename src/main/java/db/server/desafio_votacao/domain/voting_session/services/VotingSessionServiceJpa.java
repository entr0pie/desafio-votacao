package db.server.desafio_votacao.domain.voting_session.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.services.AgendaService;
import db.server.desafio_votacao.domain.voting_session.exceptions.InvalidDateException;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.repositories.VotingSessionRepository;
import lombok.AllArgsConstructor;

/**
 * Service for finding and creating voting sessions using Spring JPA.
 * 
 * @author Caio Porcel
 */
@Service
@AllArgsConstructor
public class VotingSessionServiceJpa implements VotingSessionService {

	private final VotingSessionRepository repository;
	private final AgendaService agendaService;
	private final static Logger LOGGER = LoggerFactory.getLogger(VotingSessionServiceJpa.class);

	/**
	 * Create a new voting session, specifying the end date.
	 * 
	 * @param agendaId  related agenda id.
	 * @param startDate start date of the voting session. If {@code null}, the
	 *                  current date is used.
	 * @param endDate   end date of the voting session. If {@code null}, the start
	 *                  date plus 1 minute is used.
	 * @return the created voting session.
	 * @throws AgendaNotFoundException if the requested agenda is not found.
	 * @throws InvalidDateException    if the start or end date is invalid.
	 */
	@Override
	public VotingSessionModel create(Integer agendaId, LocalDateTime startDate, LocalDateTime endDate)
			throws AgendaNotFoundException, InvalidDateException {

		LocalDateTime now = LocalDateTime.now();

		if (startDate == null) {
			LOGGER.info("Creating voting session with default start date.");
			startDate = now;
		}

		if (endDate == null) {
			LOGGER.info("Creating voting session with default end date.");
			endDate = startDate.plusMinutes(1);
		}

		if (!areDatesValid(now, startDate, endDate)) {
			LOGGER.error("Invalid date for voting session.");
			throw new InvalidDateException("Invalid date for voting session.");
		}

		AgendaModel agenda = null;

		try {
			agenda = agendaService.findById(agendaId);
		} catch (AgendaNotFoundException e) {
			LOGGER.error("Agenda not found for voting session.");
			throw new AgendaNotFoundException("Agenda not found for voting session.");
		}

		VotingSessionModel votingSession = new VotingSessionModel();
		votingSession.setAgenda(agenda);
		votingSession.setStartDate(startDate);
		votingSession.setEndDate(endDate);
		return this.repository.save(votingSession);
	}

	/**
	 * Check if the dates are valid for a voting session.
	 * 
	 * The start date must be before the current date and the end date must be after
	 * the start date.
	 * 
	 * @param startDate start date.
	 * @param endDate   end date.
	 * @return true if the dates are valid, false otherwise.
	 */
	private static boolean areDatesValid(LocalDateTime now, LocalDateTime startDate, LocalDateTime endDate) {
		boolean isAfterOrEqualNow = startDate.isAfter(now) || startDate.isEqual(now);
		boolean isEndAfterStart = endDate.isAfter(startDate);

		return isAfterOrEqualNow && isEndAfterStart;
	}

	@Override
	public VotingSessionModel findById(int id) throws VotingSessionNotFoundException {
		Optional<VotingSessionModel> votingSession = this.repository.findById(id);

		if (votingSession.isEmpty()) {
			LOGGER.error("Voting session not found.");
			throw new VotingSessionNotFoundException();
		}

		return votingSession.get();
	}

	@Override
	public Page<VotingSessionModel> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return this.repository.findAll(pageable);
	}

}
