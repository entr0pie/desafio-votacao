package db.server.desafio_votacao.domain.cpf.exceptions;

/**
 * Exception thrown when an invalid CPF is provided.
 * 
 * @author Caio Porcel
 */
public class InvalidCPFException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Invalid CPF was provided.";

	public InvalidCPFException() {
		super(DEFAULT_MESSAGE);
	}

	public InvalidCPFException(String message) {
		super(message);
	}

}
