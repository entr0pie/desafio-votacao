package db.server.desafio_votacao.domain.agenda.dtos;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAgendaResponse {
	private Integer id;
	private String title;
	private String description;

	public GetAgendaResponse(AgendaModel model) {
		this.id = model.getId();
		this.title = model.getTitle();
		this.description = model.getDescription();
	}
}
