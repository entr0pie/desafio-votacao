package db.server.desafio_votacao.domain.votation.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
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

/**
 * Entity for votes.
 * 
 * @author Caio Porcel
 */
@Data
@Entity
@Table(name = "Votes")
@NoArgsConstructor
@AllArgsConstructor
public class VoteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "session_id", referencedColumnName = "id")
	private VotingSessionModel session;

	private Boolean agrees;
}
