package db.server.desafio_votacao.domain.user.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "user-eligibility.validator")
public class UserEligibilityConfigProperties {
	private Boolean enabled;
}
