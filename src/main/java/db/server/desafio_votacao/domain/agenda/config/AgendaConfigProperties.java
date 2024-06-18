package db.server.desafio_votacao.domain.agenda.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "endpoint.agenda")
public class AgendaConfigProperties {
	private String findById;
	private String findAll;
	private String create;
	private String update;
	private String delete;
}
