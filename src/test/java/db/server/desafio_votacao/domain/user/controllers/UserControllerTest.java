package db.server.desafio_votacao.domain.user.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import db.server.desafio_votacao.domain.user.config.UserConfigProperties;
import db.server.desafio_votacao.domain.user.dtos.RegisterUserRequest;
import db.server.desafio_votacao.domain.user.exceptions.UserAlreadyRegisteredException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.user.services.UserService;

@ActiveProfiles("test")
@WebMvcTest(controllers = { UserController.class })
@EnableConfigurationProperties(UserConfigProperties.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserConfigProperties config;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String JSON = "application/json";

	@Test
	@DisplayName("Should throw AlreadyRegisteredUser when user is already registered")
	public void shouldThrow_whenUserIsAlreadyRegistered() throws Exception {
		RegisterUserRequest request = new RegisterUserRequest("email", "cpf");
		String requestData = this.objectMapper.writeValueAsString(request);

		when(this.userService.register(eq(request.getEmail()), eq(request.getCpf())))
				.thenThrow(new UserAlreadyRegisteredException("User already registered"));

		mockMvc.perform(post(this.config.getRegister()).contentType(JSON).content(requestData))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("User already registered"));
	}

	@Test
	@DisplayName("Should register a new user")
	public void shouldRegister() throws Exception {
		RegisterUserRequest request = new RegisterUserRequest("email", "cpf");
		String requestData = this.objectMapper.writeValueAsString(request);

		when(this.userService.register(eq(request.getEmail()), eq(request.getCpf())))
				.thenReturn(new UserModel(1, "email", "cpf"));

		mockMvc.perform(post(this.config.getRegister()).contentType(JSON).content(requestData))
				.andExpect(status().isOk()).andExpect(jsonPath("$.email").value("email"))
				.andExpect(jsonPath("$.cpf").value("cpf"));
	}

}
