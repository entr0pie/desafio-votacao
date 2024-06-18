package db.server.desafio_votacao.domain.votation.exceptions;

/**
 * Exception for when a user has already voted in a session.
 * 
 * @author Caio Porcel
 */
public class AlreadyVotedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "User has already voted in this session";

	public AlreadyVotedException() {
		super(DEFAULT_MESSAGE);
	}

	public AlreadyVotedException(String message) {
		super(message);
	}

}
