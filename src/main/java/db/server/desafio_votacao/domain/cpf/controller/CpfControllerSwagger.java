package db.server.desafio_votacao.domain.cpf.controller;

import org.springframework.http.ResponseEntity;

import db.server.desafio_votacao.domain.cpf.dtos.CPFValidationResponseDTO;
import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Swagger definition for {@link CPFController}.
 * 
 * @author Caio Porcel
 */
@Tag(name = "CPF", description = "Validate a CPF from a user.")
public interface CpfControllerSwagger {

	@Operation(summary = "Validate a CPF.")
	ResponseEntity<CPFValidationResponseDTO> isValid(
			@Parameter(description = "CPF for testing", example = "928.719.240-58") String cpf)
			throws InvalidCPFException, UserNotFoundException;
}
