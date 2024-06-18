package db.server.desafio_votacao.domain.cpf;

/**
 * Interface for CPF validation.
 * 
 * @author Caio Porcel
 */
public interface CPFValidator {
	/**
	 * Validates a CPF number.
	 * 
	 * @param cpf cpf number to validate.
	 * @return true if the cpf is valid, false otherwise.
	 */
	boolean isValid(String cpf);
}
