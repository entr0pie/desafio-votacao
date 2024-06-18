package db.server.desafio_votacao.domain.votation.dtos;

import db.server.desafio_votacao.domain.votation.models.VoteModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVoteResponse {
	public Integer id;
	public Integer userId;
	public Integer sessionId;
	public Boolean agrees;

	public GetVoteResponse(VoteModel vote) {
		this.id = vote.getId();
		this.userId = vote.getUser().getId();
		this.sessionId = vote.getSession().getId();
		this.agrees = vote.getAgrees();
	}
}
