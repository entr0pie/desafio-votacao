package db.server.desafio_votacao.domain.agenda.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.agenda.dtos.CreateAgendaRequest;
import db.server.desafio_votacao.domain.agenda.dtos.GetAgendaResponse;
import db.server.desafio_votacao.domain.agenda.dtos.UpdateAgendaRequest;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.services.AgendaService;
import db.server.desafio_votacao.domain.common.dtos.PageResponse;
import lombok.AllArgsConstructor;

/**
 * Controller for creating, reading, updating and deleting agendas.
 * 
 * @author Caio Porcel
 */
@RestController
@AllArgsConstructor
public class AgendaController implements AgendaControllerSwagger {

	private static final Logger LOGGER = LoggerFactory.getLogger(AgendaController.class);
	private final AgendaService agendaService;

	@Override
	@GetMapping("${endpoint.agenda.findById}")
	public ResponseEntity<GetAgendaResponse> findById(@PathVariable("id") Integer id) {
		LOGGER.info("Finding a agenda with the {} id.", id);

		AgendaModel agenda = this.agendaService.findById(id);
		GetAgendaResponse response = new GetAgendaResponse(agenda);

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("${endpoint.agenda.findAll}")
	public ResponseEntity<PageResponse<GetAgendaResponse>> findAll(@RequestParam(name = "page") Integer page,
			@RequestParam(name = "size") Integer size) {
		LOGGER.info("Finding all available agendas at page {} (size {})", page, size);

		Page<AgendaModel> agendas = this.agendaService.findAll(page, size);
		PageResponse<GetAgendaResponse> response = new PageResponse<>(agendas.map(ag -> new GetAgendaResponse(ag)));

		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("${endpoint.agenda.create}")
	public ResponseEntity<GetAgendaResponse> create(@RequestBody CreateAgendaRequest data) {
		LOGGER.info("Creating a new agenda. Title: {} | Description: {}", data.getTitle(), data.getDescription());

		AgendaModel agenda = this.agendaService.create(data.getTitle(), data.getDescription());
		GetAgendaResponse response = new GetAgendaResponse(agenda);

		return ResponseEntity.ok(response);
	}

	@Override
	@PutMapping("${endpoint.agenda.update}")
	public ResponseEntity<GetAgendaResponse> update(@PathVariable("id") Integer id,
			@RequestBody UpdateAgendaRequest data) {
		LOGGER.info("Updating the agenda with the id {}.", id);

		AgendaModel agenda = this.agendaService.update(id, data.getTitle(), data.getDescription());
		GetAgendaResponse response = new GetAgendaResponse(agenda);

		return ResponseEntity.ok(response);
	}

	@Override
	@DeleteMapping("${endpoint.agenda.delete}")
	public ResponseEntity<GetAgendaResponse> delete(@PathVariable("id") Integer id) {
		LOGGER.info("Deleting the agenda with the id {}.", id);

		AgendaModel agenda = this.agendaService.delete(id);
		GetAgendaResponse response = new GetAgendaResponse(agenda);

		return ResponseEntity.ok(response);
	}
}
