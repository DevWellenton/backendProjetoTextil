package br.api.Textil.Terceiro;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TerceiroRepository extends PagingAndSortingRepository<Terceiro, Long>,
        QuerydslPredicateExecutor<Terceiro> {
    List<Terceiro> findAll();
}
