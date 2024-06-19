package db.server.desafio_votacao.domain.common.config;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

/**
 * Configuration for the RandomGenerator.
 * 
 * @author Caio Porcel
 */
@Configuration
@AllArgsConstructor
public class RandomGeneratorConfiguration {

	@Bean
	RandomGenerator random() {
		return new SecureRandom();
	}

}
