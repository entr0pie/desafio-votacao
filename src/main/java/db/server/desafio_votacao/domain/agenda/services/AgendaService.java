package db.server.desafio_votacao.domain.agenda.services;

import org.springframework.data.domain.Page;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;

/**
 * Service for finding, creating, updating and deleting agendas (aka. themes for
 * voting).
 * 
 * @author Caio Porcel
 */
public interface AgendaService {

	/**
	 * Create a new agenda.
	 * 
	 * @param title       title of the agenda.
	 * @param description description of the agenda.
	 * @return the created agenda.
	 */
	AgendaModel create(String title, String description);

	/**
	 * Find a agenda by id.
	 * 
	 * @param id id to search for.
	 * @return found id.
	 * @throws AgendaNotFoundException if the requested agenda is not found.
	 */
	AgendaModel findById(int id) throws AgendaNotFoundException;

	/**
	 * Find all agendas (paginated).
	 * 
	 * @param page page number.
	 * @param size size of each page.
	 * @return a {@link Page} object with the found agendas.
	 */
	Page<AgendaModel> findAll(int page, int size);

	/**
	 * Update a agenda by it's id.
	 * 
	 * @param id          id of the agenda.
	 * @param title       new title for the agenda.
	 * @param description new description for the agenda.
	 * @return the updated agenda.
	 * @throws AgendaNotFoundException if the agenda is not found.
	 */
	AgendaModel update(int id, String title, String description) throws AgendaNotFoundException;

	/**
	 * Delete a agenda by it's id.
	 * 
	 * @param id id of the agenda.
	 * @return deleted agenda.
	 * @throws AgendaNotFoundException if the requested agenda is not found.
	 */
	AgendaModel delete(int id) throws AgendaNotFoundException;
}
