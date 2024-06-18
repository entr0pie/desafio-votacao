package db.server.desafio_votacao.domain.cpf.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import db.server.desafio_votacao.domain.cpf.config.CpfConfigProperties;
import db.server.desafio_votacao.domain.cpf.controller.CPFController;
import db.server.desafio_votacao.domain.cpf.enums.VoteEligibility;
import db.server.desafio_votacao.domain.cpf.exceptions.InvalidCPFException;
import db.server.desafio_votacao.domain.cpf.service.CPFValidator;
import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.services.UserEligibilityService;
import db.server.desafio_votacao.domain.user.services.UserService;

@ActiveProfiles("test")
@WebMvcTest(controllers = { CPFController.class })
@EnableConfigurationProperties(CpfConfigProperties.class)
public class CPFControllerTest {

	@MockBean
	private CPFValidator validator;

	@MockBean
	private UserService userService;

	@MockBean
	private UserEligibilityService userEligibilityService;

	@Autowired
	private CpfConfigProperties config;

	@Autowired
	private MockMvc mockMvc;

	private static final String JSON = "application/json";

	@Test
	@DisplayName("Should throw invalid CPF exception if is invalid")
	public void shouldThrowInvalidCpfException() throws Exception {
		when(this.validator.validate(eq("12345678901"))).thenThrow(new InvalidCPFException("Invalid CPF"));

		mockMvc.perform(get(this.config.getValidate(), "12345678901")).andExpect(status().isBadRequest())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("Invalid CPF"));
	}

	@Test
	@DisplayName("Should throw user not found exception")
	public void shouldThrowUserNotFoundException() throws Exception {
		when(this.validator.validate(eq("12345678901"))).thenReturn("12345678901");
		when(this.userService.findByCPF(eq("12345678901"))).thenThrow(new UserNotFoundException("User not found"));

		mockMvc.perform(get(this.config.getValidate(), "12345678901")).andExpect(status().isNotFound())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("User not found"));
	}

	@Test
	@DisplayName("Should return user status with 404")
	public void shouldReturnUserStatusWith404() throws Exception {
		when(this.validator.validate(eq("12345678901"))).thenReturn("12345678901");
		when(this.userService.findByCPF(eq("12345678901"))).thenReturn(null);
		when(this.userEligibilityService.check(eq(null))).thenReturn(VoteEligibility.UNABLE_TO_VOTE);

		mockMvc.perform(get(this.config.getValidate(), "12345678901")).andExpect(status().isBadRequest())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.status").value("UNABLE_TO_VOTE"));
	}

	@Test
	@DisplayName("Should return user status")
	public void shouldReturnUserStatus() throws Exception {
		when(this.validator.validate(eq("12345678901"))).thenReturn("12345678901");
		when(this.userService.findByCPF(eq("12345678901"))).thenReturn(null);
		when(this.userEligibilityService.check(eq(null))).thenReturn(VoteEligibility.ABLE_TO_VOTE);

		mockMvc.perform(get(this.config.getValidate(), "12345678901")).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.status").value("ABLE_TO_VOTE"));
	}
}
