package db.server.desafio_votacao.domain.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.random.RandomGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.user.models.UserModel;

@SpringBootTest
@ActiveProfiles("test")
public class RandomUserEligibilityServiceTest {

	@MockBean
	private RandomGenerator random;

	@Autowired
	private RandomUserEligibilityService service;

	@Test
	@DisplayName("Should return random eligibility")
	public void shouldReturnRandomEligibility() {
		when(this.random.nextInt(eq(VoteEligibility.values().length))).thenReturn(0);
		assertEquals(VoteEligibility.values()[0], this.service.check(new UserModel(1, "email@email.com", "cpf")),
				"Should return the value provided from the random generator");
	}

}
