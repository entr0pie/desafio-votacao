package db.server.desafio_votacao.domain.voting_session.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVotingSessionRequest {
	private Integer agendaId;

	@Null
	private LocalDateTime startDate;

	@Null
	private LocalDateTime endDate;
}
