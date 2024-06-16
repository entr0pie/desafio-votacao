package db.server.desafio_votacao.domain.user.exceptions;

/**
 * Raised when the specified user is already registered in the database.
 * 
 * @author Caio Porcel
 */
public class UserAlreadyRegistered extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "The specified user is already registered.";

	public UserAlreadyRegistered() {
		super(DEFAULT_MESSAGE);
	}

	public UserAlreadyRegistered(String message) {
		super(message);
	}

}
