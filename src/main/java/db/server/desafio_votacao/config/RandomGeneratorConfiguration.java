package db.server.desafio_votacao.config;

import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the RandomGenerator.
 * 
 * @author Caio Porcel
 */
@Configuration
public class RandomGeneratorConfiguration {

	@Bean
	RandomGenerator random() {
		return new SecureRandom();
	}
}
