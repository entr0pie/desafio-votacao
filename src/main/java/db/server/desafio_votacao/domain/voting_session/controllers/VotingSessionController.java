package db.server.desafio_votacao.domain.voting_session.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.common.dtos.PageResponse;
import db.server.desafio_votacao.domain.voting_session.dtos.CreateVotingSessionRequest;
import db.server.desafio_votacao.domain.voting_session.dtos.GetVotingSessionResponse;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.services.VotingSessionService;
import lombok.AllArgsConstructor;

/**
 * Controller for voting sessions.
 * 
 * @author Caio Porcel
 */
@RestController
@AllArgsConstructor
public class VotingSessionController implements VotingSessionControllerSwagger {

	private final VotingSessionService votingSessionService;
	private final static Logger LOGGER = LoggerFactory.getLogger(VotingSessionController.class);

	@Override
	@PostMapping("${endpoint.voting-session.create}")
	public ResponseEntity<GetVotingSessionResponse> create(@RequestBody CreateVotingSessionRequest data) {
		LOGGER.info("Creating voting session for the agenda with id {}.", data.getAgendaId());

		VotingSessionModel votingSession = votingSessionService.create(data.getAgendaId(), data.getStartDate(),
				data.getEndDate());

		GetVotingSessionResponse response = new GetVotingSessionResponse(votingSession);

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("${endpoint.voting-session.findById}")
	public ResponseEntity<GetVotingSessionResponse> findById(@PathVariable("id") Integer id) {
		LOGGER.info("Finding voting session with id {}.", id);

		VotingSessionModel votingSession = votingSessionService.findById(id);
		GetVotingSessionResponse response = new GetVotingSessionResponse(votingSession);

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("${endpoint.voting-session.findAll}")
	public ResponseEntity<PageResponse<GetVotingSessionResponse>> findAll(@RequestParam(name = "page") Integer page,
			@RequestParam(name = "size") Integer size) {
		LOGGER.info("Finding all voting sessions. Page: {} | Size: {}.", page, size);

		Page<VotingSessionModel> votingSessions = votingSessionService.findAll(page, size);
		Page<GetVotingSessionResponse> response = votingSessions.map(GetVotingSessionResponse::new);
		PageResponse<GetVotingSessionResponse> pageResponse = new PageResponse<>(response);

		return ResponseEntity.ok(pageResponse);
	}

}
