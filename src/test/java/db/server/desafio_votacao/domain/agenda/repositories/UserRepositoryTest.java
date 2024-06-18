package db.server.desafio_votacao.domain.agenda.repositories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("Should find by email")
	public void shouldFindByEmail() {
		String email = "email";
		UserModel expectedUser = new UserModel(1, email, "cpf", null);
		this.userRepository.save(expectedUser);

		UserModel receivedUser = this.userRepository.findByEmail(email).get();

		assertAll("Find by email must return the expected user.", () -> {
			assertEquals(expectedUser.getId(), receivedUser.getId(), "Id must be the same.");
			assertEquals(expectedUser.getEmail(), receivedUser.getEmail(), "Email must be the same.");
			assertEquals(expectedUser.getCpf(), receivedUser.getCpf(), "Cpf must be the same.");
		});
	}

	@Test
	@DisplayName("Should find by cpf")
	public void shouldFindByCpf() {
		String cpf = "cpf";
		UserModel expectedUser = new UserModel(1, "email", cpf, null);
		this.userRepository.save(expectedUser);

		UserModel receivedUser = this.userRepository.findByCpf(cpf).get();

		assertAll("Find by cpf must return the expected user.", () -> {
			assertEquals(expectedUser.getId(), receivedUser.getId(), "Id must be the same.");
			assertEquals(expectedUser.getEmail(), receivedUser.getEmail(), "Email must be the same.");
			assertEquals(expectedUser.getCpf(), receivedUser.getCpf(), "Cpf must be the same.");
		});
	}
}
