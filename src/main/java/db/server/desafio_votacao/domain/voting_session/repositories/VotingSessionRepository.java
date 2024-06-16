package db.server.desafio_votacao.domain.voting_session.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import db.server.desafio_votacao.domain.voting_session.models.VotingSessionModel;

public interface VotingSessionRepository
		extends CrudRepository<VotingSessionModel, Integer>, PagingAndSortingRepository<VotingSessionModel, Integer> {
}
