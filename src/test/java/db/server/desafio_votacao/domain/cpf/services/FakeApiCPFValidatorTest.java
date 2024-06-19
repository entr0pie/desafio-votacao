package db.server.desafio_votacao.domain.cpf.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.random.RandomGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;
import db.server.desafio_votacao.domain.cpf.service.FakeApiCPFValidator;

@SpringBootTest
@ActiveProfiles("test")
public class FakeApiCPFValidatorTest {

	@MockBean
	private RandomGenerator random;

	@Autowired
	private FakeApiCPFValidator fakeApiCPFValidator;

	@Test
	@DisplayName("Should reject less than eleven digits")
	public void shouldRejectLessThanElevenDigits() {
		String cpf = "1234567890";
		assertThrows(InvalidCPFException.class, () -> fakeApiCPFValidator.validate(cpf));
	}

	@Test
	@DisplayName("Should reject equal digits")
	public void shouldRejectAllDigitsEqual() {
		for (int i = 0; i < 10; i++) {
			String cpf = String.valueOf(i).repeat(11);
			assertThrows(InvalidCPFException.class, () -> fakeApiCPFValidator.validate(cpf));
		}
	}

	@Test
	@DisplayName("Should reject invalid first verification digit")
	public void shouldRejectInvalidFirstVerificationDigit() {
		String cpf = "111.222.333-96";
		assertThrows(InvalidCPFException.class, () -> fakeApiCPFValidator.validate(cpf));
	}

	@Test
	@DisplayName("Should reject invalid second verification digit")
	public void shouldRejectInvalidSecondVerificationDigit() {
		String cpf = "444.555.666-12";
		assertThrows(InvalidCPFException.class, () -> fakeApiCPFValidator.validate(cpf));
	}

	@Test
	@DisplayName("Should reject when API rejects")
	public void shouldReject_whenAPIRejects() {
		when(random.nextBoolean()).thenReturn(false);

		String cpf = "928.719.240-58";

		assertThrows(InvalidCPFException.class, () -> fakeApiCPFValidator.validate(cpf));
	}

	@Test
	@DisplayName("Should accept when valid and API accepts")
	public void shouldAccept_whenValid_andAPIAccepts() {
		when(random.nextBoolean()).thenReturn(true);

		String cpf = "928.719.240-58";

		assertDoesNotThrow(() -> {
			String validatedCPF = fakeApiCPFValidator.validate(cpf);
			assertEquals("92871924058", validatedCPF);
		});
	}

}
