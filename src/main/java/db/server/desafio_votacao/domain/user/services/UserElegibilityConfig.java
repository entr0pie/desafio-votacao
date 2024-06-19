package db.server.desafio_votacao.domain.user.services;

import java.util.random.RandomGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.user.models.UserModel;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class UserElegibilityConfig {
	private final UserEligibilityConfigProperties properties;

	@Bean
	@Primary
	UserEligibilityService userEligibilityService(RandomGenerator random) {
		return this.properties.getEnabled() ? randomUserEligibilityService(random) : mockedUserEligibilityService();
	}

	private static UserEligibilityService mockedUserEligibilityService() {
		return new UserEligibilityService() {
			@Override
			public VoteEligibility check(UserModel user) {
				return VoteEligibility.ABLE_TO_VOTE;
			}
		};

	}

	private static UserEligibilityService randomUserEligibilityService(RandomGenerator random) {
		return new RandomUserEligibilityService(random);
	}

}
