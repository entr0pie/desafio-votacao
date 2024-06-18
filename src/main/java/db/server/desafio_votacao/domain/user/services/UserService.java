package db.server.desafio_votacao.domain.user.services;

import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegisteredException;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
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
	 * @throws UserAlreadyRegisteredException if the user is already registered.
	 */
	UserModel register(String email, String cpf) throws UserAlreadyRegisteredException;

	/**
	 * Find a user by its id.
	 * 
	 * @param id id of the user.
	 * @return the found user.
	 * @throws UserNotFoundException if the user is not found.
	 */
	UserModel findById(Integer id) throws UserNotFoundException;

}
