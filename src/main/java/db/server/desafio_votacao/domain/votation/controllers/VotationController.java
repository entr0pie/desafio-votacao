package db.server.desafio_votacao.domain.votation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.votation.dtos.GetVoteResponse;
import db.server.desafio_votacao.domain.votation.dtos.VotationResults;
import db.server.desafio_votacao.domain.votation.dtos.VoteRequest;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import db.server.desafio_votacao.domain.votation.services.VoteService;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class VotationController implements VotationControllerSwagger {

	private final VoteService voteService;

	@Override
	@PostMapping("${endpoint.votation.vote}")
	public ResponseEntity<GetVoteResponse> vote(@RequestBody VoteRequest voteRequest) throws AlreadyVotedException,
			SessionClosedException, VotingSessionNotFoundException, UserNotFoundException {
		VoteModel vote = voteService.vote(voteRequest.getSessionId(), voteRequest.getUserId(), voteRequest.getAgrees());
		GetVoteResponse response = new GetVoteResponse(vote.getId(), vote.getUser().getId(), vote.getSession().getId(),
				vote.getAgrees());

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("${endpoint.votation.results}")
	public ResponseEntity<VotationResults> getResults(@PathVariable("id") Integer sessionId)
			throws VotingSessionNotFoundException {
		VotationResults results = voteService.getResults(sessionId);
		return ResponseEntity.ok(results);
	}

}
