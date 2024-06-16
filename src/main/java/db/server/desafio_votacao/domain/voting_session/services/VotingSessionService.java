package db.server.desafio_votacao.domain.voting_session.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.voting_session.exceptions.InvalidDateException;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;

/**
 * Service for finding and creating voting sessions.
 * 
 * @author Caio Porcel
 */
public interface VotingSessionService {
	/**
	 * Create a new voting session, specifying the end date.
	 * 
	 * @param agendaId  related agenda id.
	 * @param startDate start date of the voting session.
	 * @param endDate   end date of the voting session.
	 * 
	 * @return the created voting session.
	 * @throws AgendaNotFoundException if the requested agenda is not found.
	 * @throws InvalidDateException    if the start or end date is invalid.
	 */
	VotingSessionModel create(Integer agendaId, LocalDateTime startDate, LocalDateTime endDate)
			throws AgendaNotFoundException, InvalidDateException;

	/**
	 * Find a voting session by id.
	 * 
	 * @param id id to search for.
	 * @return found voting session.
	 * @throws VotingSessionNotFoundException if the requested voting session is not found.
	 */
	VotingSessionModel findById(int id) throws VotingSessionNotFoundException;

	/**
	 * Find all voting sessions (paginated).
	 * 
	 * @param page page number.
	 * @param size size of each page.
	 * @return a {@link Page} object with the found voting sessions.
	 */
	Page<VotingSessionModel> findAll(int page, int size);
}
