package db.server.desafio_votacao.domain.agenda.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "endpoint.agenda")
public class AgendaConfigProperties {
	private String findById;
	private String findAll;
	private String create;
	private String update;
	private String delete;
}
