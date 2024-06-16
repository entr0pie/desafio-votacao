package db.server.desafio_votacao.domain.user.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.user.dtos.GetUserResponse;
import db.server.desafio_votacao.domain.user.dtos.RegisterUserRequest;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.services.UserService;
import lombok.AllArgsConstructor;

/**
 * Controller for registering users.
 * 
 * @author Caio Porcel
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements UserControllerSwagger {

	private final UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Override
	@PostMapping("/register")
	public ResponseEntity<GetUserResponse> register(@RequestBody RegisterUserRequest data) {
		LOGGER.info("Registering a new user with {} email and {} CPF.", data.getEmail(), data.getCpf());

		UserModel user = this.userService.register(data.getEmail(), data.getCpf());
		GetUserResponse response = new GetUserResponse(user.getEmail(), user.getCpf());

		return ResponseEntity.ok(response);
	}

}
