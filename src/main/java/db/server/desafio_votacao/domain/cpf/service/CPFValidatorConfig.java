package db.server.desafio_votacao.domain.cpf.service;

import java.util.random.RandomGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class CPFValidatorConfig {

	private final CPFValidatorConfigProperties properties;

	@Bean
	@Primary
	CPFValidator cpfValidator(RandomGenerator random) {
		return this.properties.getEnabled() ? fakeApiCPFValidator(random) : mockedCPFValidator();
	}

	private static CPFValidator mockedCPFValidator() {
		return new CPFValidator() {
			@Override
			public String validate(String cpf) throws InvalidCPFException {
				return cpf;
			}
		};
	}

	private static CPFValidator fakeApiCPFValidator(RandomGenerator random) {
		return new FakeApiCPFValidator(random);
	}

}
