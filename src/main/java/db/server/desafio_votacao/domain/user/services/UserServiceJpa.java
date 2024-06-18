package db.server.desafio_votacao.domain.user.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegisteredException;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.repositories.UserRepository;
import lombok.AllArgsConstructor;

/**
 * Service for registering users using Spring JPA.
 * 
 * @author Caio Porcel
 */
@Service
@AllArgsConstructor
public class UserServiceJpa implements UserService {

	private final UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceJpa.class);

	@Override
	public UserModel register(String email, String cpf) throws UserAlreadyRegisteredException {
		this.userRepository.findByEmail(email).ifPresent(u -> {
			LOGGER.error("The specified email is already registered.");
			throw new UserAlreadyRegisteredException();
		});

		this.userRepository.findByCpf(cpf).ifPresent(u -> {
			LOGGER.error("The specified CPF is already registered.");
			throw new UserAlreadyRegisteredException();
		});

		UserModel user = new UserModel();
		user.setEmail(email);
		user.setCpf(cpf);

		return this.userRepository.save(user);
	}

	@Override
	public UserModel findById(Integer id) throws UserNotFoundException {
		return this.userRepository.findById(id).orElseThrow(() -> {
			LOGGER.error("The specified user was not found.");
			return new UserNotFoundException();
		});
	}
}
