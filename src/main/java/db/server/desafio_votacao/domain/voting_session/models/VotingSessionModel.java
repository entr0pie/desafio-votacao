package db.server.desafio_votacao.domain.voting_session.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
