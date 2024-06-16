package db.server.desafio_votacao.domain.voting_session.controllers;

import org.springframework.http.ResponseEntity;

import db.server.desafio_votacao.domain.common.dtos.PageResponse;
import db.server.desafio_votacao.domain.voting_session.dtos.CreateVotingSessionRequest;
import db.server.desafio_votacao.domain.voting_session.dtos.GetVotingSessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "VotingSession", description = "Start a new voting sessions.")
public interface VotingSessionControllerSwagger {

	@Operation(summary = "Create a new voting session.")
	ResponseEntity<GetVotingSessionResponse> create(@RequestBody CreateVotingSessionRequest data);

	@Operation(summary = "Find a voting session by id.")
	ResponseEntity<GetVotingSessionResponse> findById(
			@Parameter(description = "Id of the voting session.", example = "1") Integer id);

	@Operation(summary = "Get all available voting sessions, filtering by page and size.")
	ResponseEntity<PageResponse<GetVotingSessionResponse>> findAll(
			@Parameter(description = "Page number", example = "10") Integer page,
			@Parameter(description = "Size of each page", example = "15") Integer size);
}
