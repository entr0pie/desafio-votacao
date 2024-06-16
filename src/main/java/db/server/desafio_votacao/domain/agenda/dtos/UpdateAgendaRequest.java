package db.server.desafio_votacao.domain.agenda.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAgendaRequest {
	private String title;
	private String description;
}
