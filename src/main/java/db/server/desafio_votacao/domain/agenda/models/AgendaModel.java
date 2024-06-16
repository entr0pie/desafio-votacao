package db.server.desafio_votacao.domain.agenda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for agendas (theme for voting).
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String title;
	private String description;
}
