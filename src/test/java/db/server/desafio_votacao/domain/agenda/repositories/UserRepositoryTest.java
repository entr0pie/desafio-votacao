package db.server.desafio_votacao.domain.agenda.repositories;

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
		UserModel expectedUser = new UserModel(1, email, "cpf");
		this.userRepository.save(expectedUser);

		assertEquals(expectedUser, this.userRepository.findByEmail(email).get(),
				"Find by email must return the expected user.");
	}

	@Test
	@DisplayName("Should find by cpf")
	public void shouldFindByCpf() {
		String cpf = "cpf";
		UserModel expectedUser = new UserModel(1, "email", cpf);
		this.userRepository.save(expectedUser);

		assertEquals(expectedUser, this.userRepository.findByCpf(cpf).get(),
				"Find by cpf must return the expected user.");
	}
}
