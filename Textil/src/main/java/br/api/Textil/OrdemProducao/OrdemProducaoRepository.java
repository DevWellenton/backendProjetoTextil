package br.api.Textil.OrdemProducao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
public interface OrdemProducaoRepository extends PagingAndSortingRepository<OrdemProducao, Long>,
        QuerydslPredicateExecutor<OrdemProducao> {

    List<OrdemProducao> findAll();
}
