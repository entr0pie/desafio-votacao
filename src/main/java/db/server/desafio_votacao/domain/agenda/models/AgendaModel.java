package db.server.desafio_votacao.domain.agenda.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "agenda")
	@JsonIgnore
	private List<VotingSessionModel> sessions;

	public AgendaModel(Integer id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

}
