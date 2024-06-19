package db.server.desafio_votacao.domain.cpf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import db.server.desafio_votacao.domain.cpf.dtos.CPFValidationResponseDTO;
import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;
import db.server.desafio_votacao.domain.cpf.service.CPFValidator;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.services.UserEligibilityService;
import db.server.desafio_votacao.domain.user.services.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@RestController
public class CPFController implements CpfControllerSwagger {

	private final CPFValidator validator;
	private final UserService userService;
	private final UserEligibilityService userEligibilityService;
	private static final Logger LOGGER = LoggerFactory.getLogger(CPFController.class);

	@GetMapping("${endpoint.cpf.validate}")
	public ResponseEntity<CPFValidationResponseDTO> isValid(@PathVariable("cpf") String cpf)
			throws InvalidCPFException, UserNotFoundException {
		LOGGER.info("Validating CPF for voting: {}", cpf);

		String sanitizedCPF = this.validator.validate(cpf);
		UserModel user = this.userService.findByCPF(sanitizedCPF);
		VoteEligibility status = this.userEligibilityService.check(user);

		CPFValidationResponseDTO response = new CPFValidationResponseDTO(status);

		return status.equals(VoteEligibility.ABLE_TO_VOTE) ? ResponseEntity.ok(response)
				: ResponseEntity.badRequest().body(response);
	}

}
