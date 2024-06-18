package db.server.desafio_votacao.domain.votation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;

@ControllerAdvice
public class VotationControllerAdvice {

	@ExceptionHandler(AlreadyVotedException.class)
	public ResponseEntity<ExpectedErrorMessage> handleAlreadyVotedException(AlreadyVotedException e) {
		return ResponseEntity.badRequest().body(new ExpectedErrorMessage(e.getMessage()));
	}

	@ExceptionHandler(SessionClosedException.class)
	public ResponseEntity<ExpectedErrorMessage> handleSessionClosedException(SessionClosedException e) {
		return ResponseEntity.badRequest().body(new ExpectedErrorMessage(e.getMessage()));
	}

}
