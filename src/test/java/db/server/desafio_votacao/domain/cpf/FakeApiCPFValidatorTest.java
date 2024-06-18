package db.server.desafio_votacao.domain.cpf;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.random.RandomGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

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
		assertFalse(fakeApiCPFValidator.isValid(cpf), "Less than eleven digits should be rejected.");
	}

	@Test
	@DisplayName("Should reject more than eleven digits")
	public void shouldRejectAllDigitsEqual() {
		for (int i = 0; i < 10; i++) {
			String cpf = String.valueOf(i).repeat(11);
			assertFalse(fakeApiCPFValidator.isValid(cpf), "All digits equal should be rejected.");
		}
	}

	@Test
	@DisplayName("Should reject all digits equal")
	public void shouldRejectInvalidFirstVerificationDigit() {
		String cpf = "111.222.333-96";
		assertFalse(fakeApiCPFValidator.isValid(cpf), "Invalid first verification digit should be rejected.");
	}

	@Test
	@DisplayName("Should reject invalid second verification digit")
	public void shouldRejectInvalidSecondVerificationDigit() {
		String cpf = "444.555.666-12";
		assertFalse(fakeApiCPFValidator.isValid(cpf), "Invalid second verification digit should be rejected.");
	}

	@Test
	@DisplayName("Should reject when API rejects")
	public void shouldReject_whenAPIRejects() {
		when(random.nextBoolean()).thenReturn(false);

		String cpf = "928.719.240-58";

		assertFalse(fakeApiCPFValidator.isValid(cpf), "Should reject when API rejects.");
	}

	@Test
	@DisplayName("Should accept when valid and API accepts")
	public void shouldAccept_whenValid_andAPIAccepts() {
		when(random.nextBoolean()).thenReturn(true);

		String cpf = "928.719.240-58";

		assertTrue(fakeApiCPFValidator.isValid(cpf), "Should accept when API accepts.");
	}

}
