package db.server.desafio_votacao.domain.cpf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;
import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;

@ControllerAdvice
public class CPFControllerAdvice {

	@ExceptionHandler(InvalidCPFException.class)
	public ResponseEntity<ExpectedErrorMessage> handleInvalidCPFException(InvalidCPFException e) {
		ExpectedErrorMessage message = new ExpectedErrorMessage(e.getMessage());
		return ResponseEntity.badRequest().body(message);
	}
}
