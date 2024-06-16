package db.server.desafio_votacao.domain.user.services;

import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegistered;
import db.server.desafio_votacao.domain.user.models.UserModel;

/**
 * Service for registering users.
 * 
 * @author Caio Porcel
 */
public interface UserService {
	/**
	 * Register a new user.
	 * 
	 * @param email email for the user.
	 * @param cpf   password for the user.
	 * @return the created user.
	 * @throws UserAlreadyRegistered if the user is already registered.
	 */
	UserModel register(String email, String cpf) throws UserAlreadyRegistered;
}
