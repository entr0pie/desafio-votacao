package db.server.desafio_votacao.domain.agenda.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgendaRequest {
	private String title;
	private String description;
}
