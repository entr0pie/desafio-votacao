package db.server.desafio_votacao.domain.voting_session.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.common.dtos.PageResponse;
import db.server.desafio_votacao.domain.voting_session.dtos.CreateVotingSessionRequest;
import db.server.desafio_votacao.domain.voting_session.dtos.GetVotingSessionResponse;
import db.server.desafio_votacao.domain.voting_session.exceptions.InvalidDateException;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;
import db.server.desafio_votacao.domain.voting_session.services.VotingSessionService;

@ActiveProfiles("test")
@WebMvcTest(controllers = { VotingSessionController.class })
public class VotingSessionControllerTest {

	@MockBean
	private VotingSessionService votingSessionService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String JSON = "application/json";

	@Test
	@DisplayName("Should return error if one or more dates are invalid")
	public void shouldReturnError_whenDateInvalid() throws Exception {
		int agendaId = 1;
		String request = this.objectMapper.writeValueAsString(new CreateVotingSessionRequest(agendaId, null, null));

		when(this.votingSessionService.create(eq(agendaId), any(), any()))
				.thenThrow(new InvalidDateException("Error message."));

		mockMvc.perform(post("/voting-session").contentType(JSON).content(request)).andExpect(status().isBadRequest())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("Error message."));
	}

	@Test
	@DisplayName("Should return error if agenda not found")
	public void shouldReturnError_whenAgendaNotFound() throws Exception {
		int agendaId = 1;
		String request = this.objectMapper.writeValueAsString(new CreateVotingSessionRequest(agendaId, null, null));

		when(this.votingSessionService.create(eq(agendaId), any(), any()))
				.thenThrow(new AgendaNotFoundException("Error message."));

		mockMvc.perform(post("/voting-session").contentType(JSON).content(request)).andExpect(status().isNotFound())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("Error message."));
	}

	@Test
	@DisplayName("Should return error if voting session not found")
	public void shouldReturnError_whenVotingSessionNotFound() throws Exception {
		int votingSessionId = 1;

		when(this.votingSessionService.findById(eq(votingSessionId)))
				.thenThrow(new VotingSessionNotFoundException("Error message."));

		mockMvc.perform(get("/voting-session/{id}", votingSessionId)).andExpect(status().isNotFound())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("Error message."));
	}

	@Test
	@DisplayName("Should create voting session")
	public void shouldCreate() throws Exception {
		int agendaId = 1;
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now().plusMinutes(1);
		AgendaModel agenda = new AgendaModel(agendaId, "title", "description");
		VotingSessionModel votingSession = new VotingSessionModel(1, agenda, startDate, endDate);
		String request = this.objectMapper
				.writeValueAsString(new CreateVotingSessionRequest(agendaId, startDate, endDate));
		String expectedContent = this.objectMapper.writeValueAsString(votingSession);

		when(this.votingSessionService.create(eq(agendaId), eq(startDate), eq(endDate))).thenReturn(votingSession);

		mockMvc.perform(post("/voting-session").contentType(JSON).content(request)).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedContent));
	}

	@Test
	@DisplayName("Should get voting session by id")
	public void shouldGetById() throws Exception {
		int votingSessionId = 1;
		AgendaModel agenda = new AgendaModel(1, "title", "description");
		VotingSessionModel votingSession = new VotingSessionModel(votingSessionId, agenda, LocalDateTime.now(),
				LocalDateTime.now().plusMinutes(1));
		String expectedContent = this.objectMapper.writeValueAsString(votingSession);

		when(this.votingSessionService.findById(eq(votingSessionId))).thenReturn(votingSession);

		mockMvc.perform(get("/voting-session/{id}", votingSessionId)).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedContent));
	}

	@Test
	@DisplayName("Should get all voting sessions")
	public void shouldGetAll() throws Exception {
		int page = 1;
		int size = 10;

		Page<VotingSessionModel> votingSessions = new PageImpl<>(List.of(new VotingSessionModel(1,
				new AgendaModel(1, "title", "description"), LocalDateTime.now(), LocalDateTime.now().plusMinutes(1))));

		when(this.votingSessionService.findAll(eq(page), eq(size))).thenReturn(votingSessions);

		PageResponse<GetVotingSessionResponse> expectedObject = new PageResponse<GetVotingSessionResponse>(
				votingSessions.map(a -> new GetVotingSessionResponse(a)));

		String expectedContent = this.objectMapper.writeValueAsString(expectedObject);

		mockMvc.perform(get("/voting-session/").param("page", "1").param("size", "10")).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedContent));
	}

}
