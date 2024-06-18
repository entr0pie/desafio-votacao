package db.server.desafio_votacao.domain.user.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import db.server.desafio_votacao.domain.votation.models.VoteModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String cpf;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<VoteModel> votes;

	public UserModel(Integer id, String email, String cpf) {
		this.id = id;
		this.email = email;
		this.cpf = cpf;
	}
}
