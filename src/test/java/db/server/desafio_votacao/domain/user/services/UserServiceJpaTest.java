package db.server.desafio_votacao.domain.user.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.cpf.service.CPFValidator;
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

	@MockBean
	private CPFValidator cpfValidator;

	@Autowired
	private UserServiceJpa userServiceJpa;

	@Test
	@DisplayName("Should throw AlreadyRegisteredUser when email is already registered")
	public void shouldThrow_whenEmailIsAlreadyRegistered() {
		UserModel user = new UserModel(1, "email", "cpf");

		when(this.cpfValidator.validate(eq("cpf"))).thenReturn("cpf");
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

		when(this.cpfValidator.validate(eq("cpf"))).thenReturn("cpf");
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

		when(this.cpfValidator.validate(eq("cpf"))).thenReturn("cpf");
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

	@Test
	@DisplayName("Should throw UserNotFoundException when get by cpf")
	public void shoulThrowUserNotFound_whenFindByCPF() {
		when(this.userRepository.findByCpf(eq("cpf"))).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			this.userServiceJpa.findByCPF("cpf");
		}, "The service must throw UserNotFoundException when the user is not found.");
	}

	@Test
	@DisplayName("Should get user by cpf")
	public void shouldFindByCPF() {
		UserModel user = new UserModel(1, "email", "cpf");
		when(this.userRepository.findByCpf(eq("cpf"))).thenReturn(Optional.of(user));
		assertDoesNotThrow(() -> {
			UserModel result = this.userServiceJpa.findByCPF("cpf");
			assertEquals(user, result, "The return value must be the repository's return.");
		});
	}

	@Test
	@DisplayName("Should find all")
	public void shouldFindAll() {
		UserModel user1 = new UserModel(1, "email1", "cpf1");
		UserModel user2 = new UserModel(2, "email2", "cpf2");

		Pageable pageable = PageRequest.of(0, 10);
		Page<UserModel> page = new PageImpl<>(List.of(user1, user2));

		when(this.userRepository.findAll(eq(pageable))).thenReturn(page);
		assertDoesNotThrow(() -> {
			Page<UserModel> result = this.userServiceJpa.findAll(0, 10);
			assertEquals(page, result, "The return value must be the repository's return.");
		});
	}

}
