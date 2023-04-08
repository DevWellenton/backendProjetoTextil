package br.api.Textil.Usuario;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>,
        QuerydslPredicateExecutor<Usuario> {
    List<Usuario> findAll();
}
