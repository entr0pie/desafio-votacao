package db.server.desafio_votacao.domain.agenda.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import db.server.desafio_votacao.domain.agenda.exceptions.AgendaNotFoundException;
import db.server.desafio_votacao.domain.agenda.models.AgendaModel;
import db.server.desafio_votacao.domain.agenda.repositories.AgendaRepository;

@SpringBootTest(classes = { AgendaServiceJpa.class, AgendaService.class })
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class AgendaServiceJpaTest {

	@MockBean
	private AgendaRepository agendaRepository;

	@Autowired
	private AgendaServiceJpa agendaServiceJpa;

	@Test
	@DisplayName("Should create a new agenda")
	public void shouldCreateAgenda() {
		String title = "MockedTitle";
		String description = "MockedDescription";
		AgendaModel inputAgenda = new AgendaModel(null, title, description);
		AgendaModel outputAgenda = new AgendaModel(1, title, description);

		when(this.agendaRepository.save(eq(inputAgenda))).thenReturn(outputAgenda);

		assertEquals(outputAgenda, this.agendaServiceJpa.create(title, description),
				"The return value of create must be the repository's return.");
	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when agenda is not found in find by id")
	public void shouldThrowException_whenFindingById_andNotFound() {
		Integer invalidId = 10;

		when(this.agendaRepository.findById(eq(invalidId))).thenReturn(Optional.empty());

		assertThrows(AgendaNotFoundException.class, () -> {
			this.agendaServiceJpa.findById(invalidId);
		}, "Find by id must throw a exception when no agenda is found.");
	}

	@Test
	@DisplayName("Should find by id")
	public void shouldFindById() {
		Integer validId = 1;
		AgendaModel expectedAgenda = new AgendaModel(1, "MockedTitle", "MockedDescription");

		when(this.agendaRepository.findById(eq(validId))).thenReturn(Optional.of(expectedAgenda));

		assertDoesNotThrow(() -> {
			assertEquals(expectedAgenda, this.agendaServiceJpa.findById(validId),
					"The found agenda must be equal to the expected.");
		}, "Find by id must not throw any exception when agenda is found.");
	}

	@Test
	@DisplayName("Should find all")
	public void shouldFindAll() {
		int page = 0;
		int size = 10;
		Pageable expectedInput = PageRequest.of(page, size);
		Page<AgendaModel> expectedOutput = new PageImpl<>(
				List.of(new AgendaModel(1, "MockedTitle", "MockedDescription")));

		when(this.agendaRepository.findAll(eq(expectedInput))).thenReturn(expectedOutput);

		assertEquals(expectedOutput, this.agendaServiceJpa.findAll(page, size));
	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when agenda is not found in update")
	public void shouldThrowException_whenUpdating_andNotFound() {
		int invalidId = 10;

		when(this.agendaRepository.findById(eq(invalidId))).thenThrow(AgendaNotFoundException.class);

		assertThrows(AgendaNotFoundException.class, () -> {
			this.agendaServiceJpa.update(invalidId, null, null);
		}, "Update should throw not found exception.");
	}

	@Test
	@DisplayName("Should update a agenda")
	public void shouldUpdate() {
		int validId = 10;
		AgendaModel original = new AgendaModel(validId, "MockedTitle", "MockedDescription");

		String newTitle = "NewTitle";
		String newDescription = "NewDescription";
		AgendaModel updated = new AgendaModel(validId, newTitle, newDescription);

		when(this.agendaRepository.findById(eq(validId))).thenReturn(Optional.of(original));
		when(this.agendaRepository.save(eq(updated))).thenReturn(updated);

		assertEquals(updated, this.agendaServiceJpa.update(validId, newTitle, newDescription));
	}

	@Test
	@DisplayName("Should throw AgendaNotFoundException when agenda is not found in update")
	public void shouldThrowException_whenDeleting_andNotFound() {
		int invalidId = 10;

		when(this.agendaRepository.findById(eq(invalidId))).thenThrow(AgendaNotFoundException.class);

		assertThrows(AgendaNotFoundException.class, () -> {
			this.agendaServiceJpa.delete(invalidId);
		}, "Delete should throw not found exception.");
	}

	@Test
	@DisplayName("Should delete a agenda")
	public void shouldDelete() {
		int validId = 10;
		AgendaModel expectedAgenda = new AgendaModel(validId, "MockedTitle", "MockedDescription");

		when(this.agendaRepository.findById(eq(validId))).thenReturn(Optional.of(expectedAgenda));

		assertEquals(expectedAgenda, this.agendaServiceJpa.delete(validId),
				"The deleted agenda must be equal as the found one.");

		verify(this.agendaRepository, times(1)).delete(eq(expectedAgenda));
	}

}
