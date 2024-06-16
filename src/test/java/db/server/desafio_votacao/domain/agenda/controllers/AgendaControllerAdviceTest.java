package db.server.desafio_votacao.domain.agenda.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.common.dtos.ExpectedErrorMessage;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class AgendaControllerAdviceTest {

	@Autowired
	private AgendaControllerAdvice agendaControllerAdvice;

	@Test
	@DisplayName("Should handle AgendaNotFoundException and return 404 status code")
	public void testHandleHandleNotFound() {
		String expectedMessage = "Agenda not found";
		AgendaNotFoundException ex = new AgendaNotFoundException(expectedMessage);

		ResponseEntity<ExpectedErrorMessage> message = agendaControllerAdvice.handleNotFound(ex);

		assertEquals(HttpStatus.NOT_FOUND, message.getStatusCode(), "Should return 404 status code.");
		assertEquals(expectedMessage, message.getBody().getError(), "Should return expected message.");
	}
}
