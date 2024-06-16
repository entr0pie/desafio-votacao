package db.server.desafio_votacao.domain.agenda.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;

@ControllerAdvice
public class AgendaControllerAdvice {

	@ExceptionHandler(AgendaNotFoundException.class)
	public ResponseEntity<ExpectedErrorMessage> handleNotFound(AgendaNotFoundException ex) {
		ExpectedErrorMessage error = new ExpectedErrorMessage(new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
