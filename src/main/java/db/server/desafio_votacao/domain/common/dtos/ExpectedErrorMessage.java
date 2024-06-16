package db.server.desafio_votacao.domain.common.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Message returned for expected (previsible) errors in the API.
 * 
 * @author Caio Porcel
 */
@Data
@AllArgsConstructor
public class ExpectedErrorMessage {
	private LocalDateTime timestamp;
	private String error;

	public ExpectedErrorMessage(String error) {
		this.timestamp = LocalDateTime.now();
		this.error = error;
	}
}
