package db.server.desafio_votacao.domain.voting_session.exceptions;

/**
 * Exception for invalid date for voting session.
 * 
 * @author Caio Porcel
 */
public class InvalidDateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Invalid date for voting session.";

	public InvalidDateException() {
		super(MESSAGE);
	}

	public InvalidDateException(String message) {
		super(message);
	}
}
