package db.server.desafio_votacao.domain.agenda.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import db.server.desafio_votacao.domain.agenda.dtos.CreateAgendaRequest;
import db.server.desafio_votacao.domain.agenda.dtos.GetAgendaResponse;
import db.server.desafio_votacao.domain.agenda.dtos.UpdateAgendaRequest;
import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.services.AgendaService;
import db.server.desafio_votacao.domain.common.dtos.PageResponse;

@ActiveProfiles("test")
@WebMvcTest(controllers = { AgendaController.class })
public class AgendaControllerTest {

	@MockBean
	private AgendaService agendaService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String JSON = "application/json";

	@Test
	@DisplayName("Should find agenda by id")
	public void shouldFindById() throws Exception {
		int validId = 10;
		AgendaModel expectedAgenda = new AgendaModel(validId, "MockedTitle", "MockedDescription");
		String expectedContent = this.objectMapper.writeValueAsString(expectedAgenda);

		when(this.agendaService.findById(eq(validId))).thenReturn(expectedAgenda);

		mockMvc.perform(get("/agenda/{id}", validId)).andExpect(status().isOk()).andExpect(content().contentType(JSON))
				.andExpect(content().json(expectedContent));
	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when agenda not found")
	public void shouldThrowException_whenAgendaNotFoundById() throws Exception {
		int invalidId = 10;

		when(this.agendaService.findById(eq(invalidId)))
				.thenThrow(new AgendaNotFoundException("The requested agenda could not be found."));

		mockMvc.perform(get("/agenda/{id}", invalidId)).andExpect(status().isNotFound())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested agenda could not be found."));
	}

	@Test
	@DisplayName("Should find all agendas")
	public void shouldFindAll() throws Exception {
		int page = 1;
		int size = 10;

		Page<AgendaModel> agendas = new PageImpl<>(List.of(new AgendaModel(1, "MockedTitle", "MockedDescription")));
		PageResponse<GetAgendaResponse> expectedObject = new PageResponse<GetAgendaResponse>(
				agendas.map(a -> new GetAgendaResponse(a)));
		String expectedContent = this.objectMapper.writeValueAsString(expectedObject);

		when(this.agendaService.findAll(eq(page), eq(size))).thenReturn(agendas);

		mockMvc.perform(get("/agenda/").param("page", "1").param("size", "10")).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedContent));
	}

	@Test
	@DisplayName("Should create a agenda")
	public void shouldCreate() throws Exception {
		int id = 1;
		String title = "TestTitle";
		String description = "TestDescription";

		AgendaModel agenda = new AgendaModel(id, title, description);
		CreateAgendaRequest request = new CreateAgendaRequest(title, description);
		String requestContent = this.objectMapper.writeValueAsString(request);
		GetAgendaResponse expectedResponse = new GetAgendaResponse(agenda);
		String expectedContent = this.objectMapper.writeValueAsString(expectedResponse);

		when(this.agendaService.create(eq(title), eq(description))).thenReturn(agenda);

		mockMvc.perform(post("/agenda/").contentType(JSON).content(requestContent)).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedContent));

	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when not found in update")
	public void shouldThrowException_whenNotFoundInUpdate() throws Exception {
		int invalidId = 10;
		String title = "MockedTitle";
		String description = "MockedDescription";
		UpdateAgendaRequest request = new UpdateAgendaRequest(title, description);
		String requestContent = this.objectMapper.writeValueAsString(request);

		when(this.agendaService.update(eq(invalidId), eq(title), eq(description)))
				.thenThrow(new AgendaNotFoundException("The requested agenda could not be found."));

		mockMvc.perform(put("/agenda/{id}", invalidId).contentType(JSON).content(requestContent))
				.andExpect(status().isNotFound()).andExpect(content().contentType(JSON))
				.andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested agenda could not be found."));
	}

	@Test
	@DisplayName("Should update a agenda")
	public void shouldUpdate() throws Exception {
		int invalidId = 10;
		String title = "MockedTitle";
		String description = "MockedDescription";
		UpdateAgendaRequest request = new UpdateAgendaRequest(title, description);
		String requestContent = this.objectMapper.writeValueAsString(request);
		AgendaModel agenda = new AgendaModel(invalidId, title, description);
		String expectedContent = this.objectMapper.writeValueAsString(new GetAgendaResponse(agenda));

		when(this.agendaService.update(eq(invalidId), eq(title), eq(description))).thenReturn(agenda);

		mockMvc.perform(put("/agenda/{id}", invalidId).contentType(JSON).content(requestContent))
				.andExpect(status().isOk()).andExpect(content().contentType(JSON))
				.andExpect(content().json(expectedContent));
	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when not found in delete")
	public void shouldThrowException_whenNotFoundInDelete() throws Exception {
		int invalidId = 10;

		when(this.agendaService.delete(eq(invalidId)))
				.thenThrow(new AgendaNotFoundException("The requested agenda could not be found."));

		mockMvc.perform(delete("/agenda/{id}", invalidId)).andExpect(status().isNotFound())
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested agenda could not be found."));
	}

	@Test
	@DisplayName("Should delete a agenda")
	public void shouldDelete() throws Exception {
		int invalidId = 10;
		String title = "MockedTitle";
		String description = "MockedDescription";
		AgendaModel agenda = new AgendaModel(invalidId, title, description);
		String expectedResponse = this.objectMapper.writeValueAsString(new GetAgendaResponse(agenda));

		when(this.agendaService.delete(eq(invalidId))).thenReturn(agenda);

		mockMvc.perform(delete("/agenda/{id}", invalidId)).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(expectedResponse));
	}

}
