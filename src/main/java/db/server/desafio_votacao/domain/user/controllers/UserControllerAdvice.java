package db.server.desafio_votacao.domain.user.controllers;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;
import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegistered;

@ControllerAdvice
public class UserControllerAdvice {

	@ExceptionHandler(UserAlreadyRegistered.class)
	public ResponseEntity<ExpectedErrorMessage> handleAlreadyRegistered(UserAlreadyRegistered ex) {
		ExpectedErrorMessage error = new ExpectedErrorMessage(new Date(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}