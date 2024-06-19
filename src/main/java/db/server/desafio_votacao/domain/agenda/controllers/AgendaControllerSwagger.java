package db.server.desafio_votacao.domain.agenda.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import db.server.desafio_votacao.domain.agenda.dtos.CreateAgendaRequest;
import db.server.desafio_votacao.domain.agenda.dtos.GetAgendaResponse;
import db.server.desafio_votacao.domain.agenda.dtos.UpdateAgendaRequest;
import db.server.desafio_votacao.domain.common.dtos.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Swagger definition for {@link AgendaController}.
 * 
 * @author Caio Porcel
 */
@Tag(name = "Agenda", description = "Create, read, update and delete agendas (aka. themes for voting).")
public interface AgendaControllerSwagger {

	@Operation(summary = "Find a Agenda by it's id.")
	ResponseEntity<GetAgendaResponse> findById(@Parameter(description = "Id of the agenda", example = "1") Integer id);

	@Operation(summary = "Get all available agendas, filtering by page and size.")
	ResponseEntity<PageResponse<GetAgendaResponse>> findAll(
			@Parameter(description = "Page number", example = "0") Integer page,
			@Parameter(description = "Size of each page", example = "15") Integer size);

	@Operation(summary = "Create a new agenda.")
	ResponseEntity<GetAgendaResponse> create(@RequestBody CreateAgendaRequest data);

	@Operation(summary = "Update a agenda by id.")
	ResponseEntity<GetAgendaResponse> update(@Parameter(description = "Id of the agenda", example = "1") Integer id,
			@RequestBody UpdateAgendaRequest data);

	@Operation(summary = "Delete a agenda by id.")
	ResponseEntity<GetAgendaResponse> delete(@Parameter(description = "Id of the agenda", example = "1") Integer id);
}
