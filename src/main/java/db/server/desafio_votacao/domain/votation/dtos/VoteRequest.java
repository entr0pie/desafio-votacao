package db.server.desafio_votacao.domain.votation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
	private Integer sessionId;
	private Integer userId;
	private Boolean agrees;
}
