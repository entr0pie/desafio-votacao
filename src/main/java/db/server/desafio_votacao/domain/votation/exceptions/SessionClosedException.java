package db.server.desafio_votacao.domain.votation.exceptions;

/**
 * Exception for when a user tries to vote in a session that is closed.
 * 
 * @author Caio Porcel
 */
public class SessionClosedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Session is closed.";

	public SessionClosedException() {
		super(DEFAULT_MESSAGE);
	}

	public SessionClosedException(String message) {
		super(message);
	}
}
