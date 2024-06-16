package db.server.desafio_votacao.domain.voting_session.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;
import db.server.desafio_votacao.domain.voting_session.exceptions.InvalidDateException;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;

@ControllerAdvice
public class VotingSessionControllerAdvice {

	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ExpectedErrorMessage> handleInvalidDateException(InvalidDateException e) {
		return ResponseEntity.badRequest().body(new ExpectedErrorMessage(e.getLocalizedMessage()));
	}

	@ExceptionHandler(VotingSessionNotFoundException.class)
	public ResponseEntity<ExpectedErrorMessage> handleVotingSessionNotFoundException(VotingSessionNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExpectedErrorMessage(e.getMessage()));
	}

}
