package db.server.desafio_votacao.domain.common.dtos;

import java.util.Date;

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
	private Date timestamp;
	private String error;
}
