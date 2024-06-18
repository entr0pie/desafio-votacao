package db.server.desafio_votacao.domain.voting_session.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "VotingSessions")
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "agenda_id", referencedColumnName = "id")
	@JsonBackReference
	private AgendaModel agenda;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	public VotingSessionModel(Integer id, AgendaModel agenda, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = id;
		this.agenda = agenda;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "session")
	private List<VoteModel> votes;
}
