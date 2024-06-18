package db.server.desafio_votacao.domain.user.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_MESSAGE = "User not found.";

	public UserNotFoundException() {
		this(DEFAULT_MESSAGE);
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
