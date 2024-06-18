package db.server.desafio_votacao.domain.user.services;

import java.util.random.RandomGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.user.models.UserModel;
import lombok.AllArgsConstructor;

/**
 * Service that checks if a user is eligible to vote in a random way.
 * 
 * @author Caio Porcel
 */
@Service
@AllArgsConstructor
public class RandomUserEligibilityService implements UserEligibilityService {

	private final RandomGenerator random;
	private static final Logger LOGGER = LoggerFactory.getLogger(RandomUserEligibilityService.class);

	@Override
	public VoteEligibility check(UserModel user) {
		LOGGER.debug("Checking user eligibility (based on the north winds): {}", user.getCpf());
		VoteEligibility[] values = VoteEligibility.values();
		int randomIndex = this.random.nextInt(values.length);
		return values[randomIndex];
	}

}
