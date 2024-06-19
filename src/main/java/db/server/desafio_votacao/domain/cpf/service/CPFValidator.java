package db.server.desafio_votacao.domain.cpf.service;

import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;

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
	 * @return validated and sanitized CPF number.
	 * @throws InvalidCPFException if the CPF number is invalid.
	 */
	String validate(String cpf) throws InvalidCPFException;
}
