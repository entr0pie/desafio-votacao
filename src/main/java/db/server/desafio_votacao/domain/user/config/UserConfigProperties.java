package db.server.desafio_votacao.domain.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ConfigurationProperties(prefix = "endpoint.user")
public class UserConfigProperties {
	private String register;
}
