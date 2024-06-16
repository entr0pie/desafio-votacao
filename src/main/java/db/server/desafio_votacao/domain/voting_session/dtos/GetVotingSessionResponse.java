package db.server.desafio_votacao.domain.voting_session.dtos;

import java.time.LocalDateTime;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVotingSessionResponse {
	private Integer id;
	private AgendaModel agenda;
	private LocalDateTime startDate;
	private LocalDateTime endDate;

	public GetVotingSessionResponse(VotingSessionModel votingSession) {
		this.id = votingSession.getId();
		this.agenda = votingSession.getAgenda();
		this.startDate = votingSession.getStartDate();
		this.endDate = votingSession.getEndDate();
	}
}
