package db.server.desafio_votacao.domain.user.services;

import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.user.models.UserModel;

/**
 * Checks if a user is eligible to vote.
 * 
 * @author Caio Porcel
 */
public interface UserEligibilityService {

	/**
	 * Checks if a user is eligible to vote.
	 * 
	 * @param user The user to be checked.
	 * @return The user's eligibility to vote.
	 */
	VoteEligibility check(UserModel user);
}
