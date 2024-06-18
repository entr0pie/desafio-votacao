package db.server.desafio_votacao.domain.user.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegisteredException;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.repositories.UserRepository;

@SpringBootTest(classes = { UserServiceJpa.class, UserService.class })
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class UserServiceJpaTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserServiceJpa userServiceJpa;

	@Test
	@DisplayName("Should throw AlreadyRegisteredUser when email is already registered")
	public void shouldThrow_whenEmailIsAlreadyRegistered() {
		UserModel user = new UserModel(1, "email", "cpf");

		when(this.userRepository.findByEmail(eq("email"))).thenReturn(Optional.of(user));
		when(this.userRepository.findByCpf(eq("cpf"))).thenReturn(Optional.empty());

		assertThrows(UserAlreadyRegisteredException.class, () -> {
			this.userServiceJpa.register("email", "cpf");
		}, "The service must throw AlreadyRegisteredUser when the email is already registered.");
	};

	@Test
	@DisplayName("Should throw AlreadyRegisteredUser when cpf is already registered")
	public void shouldThrow_whenCpfIsAlreadyRegistered() {
		UserModel user = new UserModel(1, "email", "cpf");

		when(this.userRepository.findByEmail(eq("email"))).thenReturn(Optional.empty());
		when(this.userRepository.findByCpf(eq("cpf"))).thenReturn(Optional.of(user));

		assertThrows(UserAlreadyRegisteredException.class, () -> {
			this.userServiceJpa.register("email", "cpf");
		}, "The service must throw AlreadyRegisteredUser when the cpf is already registered.");
	};

	@Test
	@DisplayName("Should register user")
	public void shouldRegisterUser() {
		UserModel user = new UserModel(1, "email", "cpf");
		UserModel inputUser = new UserModel(null, "email", "cpf");

		when(this.userRepository.findByEmail(eq("email"))).thenReturn(Optional.empty());
		when(this.userRepository.findByCpf(eq("cpf"))).thenReturn(Optional.empty());
		when(this.userRepository.save(eq(inputUser))).thenReturn(user);

		assertDoesNotThrow(() -> {
			UserModel result = this.userServiceJpa.register("email", "cpf");
			assertEquals(user, result, "The return value must be the repository's return.");
		});
	};

	@Test
	@DisplayName("Should throw UserNotFoundException when get by id")
	public void shouldThrowUserNotFoundException_whenGetById() {
		when(this.userRepository.findById(eq(1))).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			this.userServiceJpa.findById(1);
		}, "The service must throw UserNotFoundException when the user is not found.");
	}

	@Test
	@DisplayName("Should get user by id")
	public void shouldGetUserById() {
		UserModel user = new UserModel(1, "email", "cpf");
		when(this.userRepository.findById(eq(1))).thenReturn(Optional.of(user));
		assertDoesNotThrow(() -> {
			UserModel result = this.userServiceJpa.findById(1);
			assertEquals(user, result, "The return value must be the repository's return.");
		});
	}

}
