package db.server.desafio_votacao.domain.votation.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.server.desafio_votacao.domain.user.exceptions.UserNotFoundException;
import db.server.desafio_votacao.domain.user.models.UserModel;
import db.server.desafio_votacao.domain.votation.dtos.GetVoteResponse;
import db.server.desafio_votacao.domain.votation.dtos.VotationResults;
import db.server.desafio_votacao.domain.votation.dtos.VoteRequest;
import db.server.desafio_votacao.domain.votation.exceptions.AlreadyVotedException;
import db.server.desafio_votacao.domain.votation.exceptions.SessionClosedException;
import db.server.desafio_votacao.domain.votation.models.VoteModel;
import db.server.desafio_votacao.domain.votation.services.VoteService;
import db.server.desafio_votacao.domain.voting_session.exceptions.VotingSessionNotFoundException;
import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;

@ActiveProfiles("test")
@WebMvcTest(controllers = { VotationController.class })
public class VotationControllerTest {

	@MockBean
	private VoteService voteService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String JSON = "application/json";

	@Test
	@DisplayName("Should throw already voted exception when voting")
	public void shouldThrowAlreadyVotedException_whenVoting() throws Exception {
		VoteRequest voteRequest = new VoteRequest(1, 1, true);
		String requestContent = this.objectMapper.writeValueAsString(voteRequest);

		when(this.voteService.vote(eq(1), eq(1), eq(true)))
				.thenThrow(new AlreadyVotedException("The user has already voted in this session."));

		mockMvc.perform(post("/votes", 1, 1, true).contentType(JSON).content(requestContent))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(JSON))
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The user has already voted in this session."));
	}

	@Test
	@DisplayName("Should throw session closed exception when voting")
	public void shouldThrowSessionClosedException_whenVoting() throws Exception {
		VoteRequest voteRequest = new VoteRequest(1, 1, true);
		String requestContent = this.objectMapper.writeValueAsString(voteRequest);

		when(this.voteService.vote(eq(1), eq(1), eq(true)))
				.thenThrow(new SessionClosedException("The session is closed."));

		mockMvc.perform(post("/votes", 1, 1, true).contentType(JSON).content(requestContent))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(JSON))
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The session is closed."));
	}

	@Test
	@DisplayName("Should throw voting session not found exception when voting")
	public void shouldThrowVotingSessionNotFoundException_whenVoting() throws Exception {
		VoteRequest voteRequest = new VoteRequest(1, 1, true);
		String requestContent = this.objectMapper.writeValueAsString(voteRequest);

		when(this.voteService.vote(eq(1), eq(1), eq(true)))
				.thenThrow(new VotingSessionNotFoundException("The requested voting session was not found."));

		mockMvc.perform(post("/votes", 1, 1, true).contentType(JSON).content(requestContent))
				.andExpect(status().isNotFound()).andExpect(content().contentType(JSON))
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested voting session was not found."));
	}

	@Test
	@DisplayName("Should throw user not found exception when voting")
	public void shouldThrowUserNotFoundException_whenVoting() throws Exception {
		VoteRequest voteRequest = new VoteRequest(1, 1, true);
		String requestContent = this.objectMapper.writeValueAsString(voteRequest);

		when(this.voteService.vote(eq(1), eq(1), eq(true)))
				.thenThrow(new UserNotFoundException("The requested user was not found."));

		mockMvc.perform(post("/votes", 1, 1, true).contentType(JSON).content(requestContent))
				.andExpect(status().isNotFound()).andExpect(content().contentType(JSON))
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested user was not found."));
	}

	@Test
	@DisplayName("Should vote")
	public void shouldVote() throws Exception {
		VoteRequest voteRequest = new VoteRequest(1, 1, true);
		String requestContent = this.objectMapper.writeValueAsString(voteRequest);

		VoteModel vote = new VoteModel(1, new UserModel(1, "email", "123"), new VotingSessionModel(1, null, null, null),
				true);

		GetVoteResponse response = new GetVoteResponse(vote);
		String responseContent = this.objectMapper.writeValueAsString(response);

		when(this.voteService.vote(eq(1), eq(1), eq(true))).thenReturn(vote);

		mockMvc.perform(post("/votes").contentType(JSON).content(requestContent)).andExpect(status().isOk())
				.andExpect(content().contentType(JSON)).andExpect(content().json(responseContent));
	}

	@Test
	@DisplayName("Should throw VotingSessionNotFoundException when get results")
	public void shouldThrowVotingSessionNotFoundException_whenGetResults() throws Exception {
		when(this.voteService.getResults(eq(1)))
				.thenThrow(new VotingSessionNotFoundException("The requested voting session was not found."));

		mockMvc.perform(get("/votes/{id}", 1)).andExpect(status().isNotFound()).andExpect(content().contentType(JSON))
				.andExpect(content().contentType(JSON)).andExpect(jsonPath("$.timestamp").isNotEmpty())
				.andExpect(jsonPath("$.error").value("The requested voting session was not found."));
	}

	@Test
	@DisplayName("Should get results")
	public void shouldGetResults() throws Exception {
		VotationResults votationResults = new VotationResults(null, 1, 1, 1);
		String responseContent = this.objectMapper.writeValueAsString(votationResults);

		when(this.voteService.getResults(eq(1))).thenReturn(votationResults);

		mockMvc.perform(get("/votes/{id}", 1)).andExpect(status().isOk()).andExpect(content().contentType(JSON))
				.andExpect(content().json(responseContent));
	}

}
