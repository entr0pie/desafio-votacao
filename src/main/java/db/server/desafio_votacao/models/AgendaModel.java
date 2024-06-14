package db.server.desafio_votacao.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for a agenda (theme for voting).
 * 
 * @author Caio Porcel
 */
@Data
@Entity
@Table(name = "Agendas")
@NoArgsConstructor
@AllArgsConstructor
public class AgendaModel {

	@Id
	private Integer id;
	private String title;
	private String description;
}
