package db.server.desafio_votacao.domain.voting_session.exceptions;

/*
 * Raised when the required voting session is not found.
 * 
 * @author Caio Porcel
 */
public class VotingSessionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "The requested voting session could not be found.";

	public VotingSessionNotFoundException() {
		super(DEFAULT_MESSAGE);
	}

	public VotingSessionNotFoundException(String message) {
		super(message);
	}
}
