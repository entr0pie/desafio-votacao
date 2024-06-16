package db.server.desafio_votacao.domain.agenda.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import db.server.desafio_votacao.domain.agenda.models.AgendaModel;

public interface AgendaRepository
		extends PagingAndSortingRepository<AgendaModel, Integer>, CrudRepository<AgendaModel, Integer> {
}
