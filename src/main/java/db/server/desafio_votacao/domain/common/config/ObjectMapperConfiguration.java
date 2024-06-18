package db.server.desafio_votacao.domain.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration for jackson's {@link ObjectMapper}.
 * 
 * @author Caio Porcel
 */
@Configuration
public class ObjectMapperConfiguration {

	@Bean
	@Profile("test")
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
