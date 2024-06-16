package db.server.desafio_votacao.domain.agenda.exceptions;

/**
 * Raised when the required agenda is not found.
 * 
 * @author Caio Porcel
 */
public class AgendaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "The requested agenda could not be found.";

	public AgendaNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public AgendaNotFoundException(String message) {
		super(message);
	}

}
