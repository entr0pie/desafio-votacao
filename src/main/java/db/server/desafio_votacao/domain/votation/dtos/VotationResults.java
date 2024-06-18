package db.server.desafio_votacao.domain.votation.dtos;

import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotationResults {
	private VotingSessionModel session;
	private Integer agrees;
	private Integer disagrees;
	private Integer totalVotes;
}
