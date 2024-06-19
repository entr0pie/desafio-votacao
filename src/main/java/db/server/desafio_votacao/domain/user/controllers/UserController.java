package db.server.desafio_votacao.domain.user.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.common.dtos.PageResponse;
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
@AllArgsConstructor
public class UserController implements UserControllerSwagger {

	private final UserService userService;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Override
	@PostMapping("${endpoint.user.register}")
	public ResponseEntity<GetUserResponse> register(@RequestBody RegisterUserRequest data) {
		LOGGER.info("Registering a new user with {} email and {} CPF.", data.getEmail(), data.getCpf());

		UserModel user = this.userService.register(data.getEmail(), data.getCpf());
		GetUserResponse response = new GetUserResponse(user.getId(), user.getEmail(), user.getCpf());

		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("${endpoint.user.find-all}")
	public ResponseEntity<PageResponse<GetUserResponse>> findAll(@RequestParam(name = "page") Integer page,
			@RequestParam(name = "size") Integer size) {
		LOGGER.info("Finding all users.");

		Page<UserModel> users = this.userService.findAll(page, size);
		Page<GetUserResponse> response = users.map(u -> new GetUserResponse(u.getId(), u.getEmail(), u.getCpf()));
		PageResponse<GetUserResponse> pageResponse = new PageResponse<>(response);

		return ResponseEntity.ok(pageResponse);
	}

}
