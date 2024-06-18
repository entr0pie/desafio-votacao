package db.server.desafio_votacao.domain.cpf.dtos;

import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CPFValidationResponseDTO {
	private VoteEligibility status;
}
