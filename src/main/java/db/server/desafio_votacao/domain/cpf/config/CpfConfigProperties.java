package db.server.desafio_votacao.domain.cpf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "endpoint.cpf")
public class CpfConfigProperties {
	private String validate;
}
