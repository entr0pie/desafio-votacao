package db.server.desafio_votacao.domain.user.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;
import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegisteredException;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;

@ControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(UserAlreadyRegisteredException.class)
	public ResponseEntity<ExpectedErrorMessage> handleAlreadyRegistered(UserAlreadyRegisteredException ex) {
		ExpectedErrorMessage error = new ExpectedErrorMessage(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExpectedErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {
		ExpectedErrorMessage error = new ExpectedErrorMessage(LocalDateTime.now(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}