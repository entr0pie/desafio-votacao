package db.server.desafio_votacao.domain.votation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.votation.dtos.GetVoteResponse;
import db.server.desafio_votacao.domain.votation.dtos.VotationResults;
import db.server.desafio_votacao.domain.votation.dtos.VoteRequest;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Votation", description = "Vote for a specific agenda.")
public interface VotationControllerSwagger {

	@Operation(summary = "Vote for a specific agenda.")
	ResponseEntity<GetVoteResponse> vote(@RequestBody VoteRequest voteRequest)
			throws AlreadyVotedException, SessionClosedException, VotingSessionNotFoundException, UserNotFoundException;

	@Operation(summary = "Get the results for a votation by id")
	ResponseEntity<VotationResults> getResults(@Parameter(description = "Id of the votation.") Integer sessionId)
			throws VotingSessionNotFoundException;
}
