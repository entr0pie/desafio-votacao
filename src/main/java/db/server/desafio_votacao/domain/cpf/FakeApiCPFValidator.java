package db.server.desafio_votacao.domain.cpf;

import java.util.random.RandomGenerator;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * CPF Validator which uses an (fake) external API and CPF structure for the
 * validation.
 * 
 * @implNote This class is a placeholder for an external API call to validate
 *           the CPF. It is not implemented and always returns a random boolean
 *           value.
 * 
 * @implNote For a real implementation, both Feign and RestTemplate can be used.
 * 
 * @see <a href=
 *      "https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/">Feign</a>
 * @see <a href=
 *      "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html">RestTemplate</a>
 * 
 * @author Caio Porcel
 */
@Service
@AllArgsConstructor
public class FakeApiCPFValidator implements CPFValidator {

	private final RandomGenerator random;

	@Override
	public boolean isValid(String cpf) {
		return hasValidStructure(cpf) && isApiValid(cpf);
	}

	/**
	 * Check if the CPF has a valid structure.
	 * 
	 * This method checks if the CPF has 11 digits, if all digits are not the same
	 * and if the verification digits (DVs) are correct.
	 * 
	 * @param cpf CPF to validate.
	 * @return true if the CPF has a valid structure, false otherwise.
	 */
	private boolean hasValidStructure(String cpf) {
		// Remove non-digit characters from the CPF.
		cpf = cpf.replaceAll("[^\\d]", "");

		if (cpf.length() != 11) {
			return false;
		}

		// Reject all CPFs with all digits equal
		if (cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		int[] numbers = cpf.chars().map(Character::getNumericValue).toArray();
		int sum = 0;
		int mod;

		// Calculate the first verifier digit
		for (int i = 0; i < 9; i++) {
			sum += numbers[i] * (10 - i);
		}
		mod = sum % 11;
		int firstVerifier = mod < 2 ? 0 : 11 - mod;

		// Calculate the second verifier digit
		sum = 0;
		for (int i = 0; i < 10; i++) {
			sum += numbers[i] * (11 - i);
		}
		mod = sum % 11;
		int secondVerifier = mod < 2 ? 0 : 11 - mod;

		return numbers[9] == firstVerifier && numbers[10] == secondVerifier;
	}

	/**
	 * Check if the CPF is valid using an external API.
	 * 
	 * @implNote This method is a placeholder for an external API call to validate
	 *           the CPF. It is not implemented and always returns a random boolean
	 *           value.
	 * 
	 * @param cpf CPF to validate.
	 * @return true if the CPF is valid, false otherwise.
	 */
	private boolean isApiValid(String cpf) {
		return this.random.nextBoolean();
	}
}
