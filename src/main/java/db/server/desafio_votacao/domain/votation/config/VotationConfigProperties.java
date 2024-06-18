package db.server.desafio_votacao.domain.votation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "endpoint.votation")
public class VotationConfigProperties {
	private String vote;
	private String results;
}
