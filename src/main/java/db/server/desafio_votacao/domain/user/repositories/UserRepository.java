package db.server.desafio_votacao.domain.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import db.server.desafio_votacao.domain.user.models.UserModel;

/**
 * Repository for the user entity.
 */
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	/**
	 * Find a user by its email.
	 * 
	 * @param email The email to search for.
	 * @return The user with the specified email.
	 */
	Optional<UserModel> findByEmail(String email);

	/**
	 * Find a user by its CPF.
	 * 
	 * @param cpf The CPF to search for.
	 * @return The user with the specified CPF.
	 */
	Optional<UserModel> findByCpf(String cpf);
}
