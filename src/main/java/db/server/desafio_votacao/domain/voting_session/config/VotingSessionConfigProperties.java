package db.server.desafio_votacao.domain.voting_session.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "endpoint.voting-session")
public class VotingSessionConfigProperties {
	private String create;
	private String findById;
	private String findAll;

}
