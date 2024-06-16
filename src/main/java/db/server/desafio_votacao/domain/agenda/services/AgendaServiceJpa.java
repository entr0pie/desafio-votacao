package db.server.desafio_votacao.domain.agenda.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.repositories.AgendaRepository;
import lombok.AllArgsConstructor;

/**
 * Service for finding, creating, updating and deleting agendas using Spring
 * Data JPA.
 * 
 * @author Caio Porcel
 */
@Service
@AllArgsConstructor
public class AgendaServiceJpa implements AgendaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaServiceJpa.class);
	private final AgendaRepository repository;

	@Override
	public AgendaModel create(String title, String description) {
		AgendaModel model = new AgendaModel();
		model.setTitle(title);
		model.setDescription(description);
		return this.repository.save(model);
	}

	@Override
	public AgendaModel findById(int id) throws AgendaNotFoundException {
		Optional<AgendaModel> agenda = this.repository.findById(id);

		if (agenda.isEmpty()) {
			LOGGER.error("The requested agenda could not be found.");
			throw new AgendaNotFoundException();
		}

		return agenda.get();
	}

	@Override
	public Page<AgendaModel> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return this.repository.findAll(pageable);
	}

	@Override
	public AgendaModel update(int id, String title, String description) throws AgendaNotFoundException {
		AgendaModel agenda = this.findById(id);

		agenda.setTitle(title);
		agenda.setDescription(description);

		return this.repository.save(agenda);
	}

	@Override
	public AgendaModel delete(int id) throws AgendaNotFoundException {
		AgendaModel agenda = this.findById(id);
		this.repository.delete(agenda);
		return agenda;
	}

}
