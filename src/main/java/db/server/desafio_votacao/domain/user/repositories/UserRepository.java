package db.server.desafio_votacao.domain.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import db.server.desafio_votacao.domain.user.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
	Optional<UserModel> findByEmail(String email);

	Optional<UserModel> findByCpf(String cpf);
}
