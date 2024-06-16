package db.server.desafio_votacao.domain.user.controllers;

import org.springframework.http.ResponseEntity;

import db.server.desafio_votacao.domain.user.dtos.GetUserResponse;
import db.server.desafio_votacao.domain.user.dtos.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Swagger definition for {@link UserController}.
 * 
 * @author Caio Porcel
 */
@Tag(name = "User", description = "Register a new user.")
public interface UserControllerSwagger {
	@Operation(summary = "Register a new user.")
	ResponseEntity<GetUserResponse> register(@RequestBody RegisterUserRequest data);
}