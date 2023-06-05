package br.api.Textil.Usuario;

import br.api.Textil.Usuario.models.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UsuarioRepository extends PagingAndSortingRepository<User, Long>,
        QuerydslPredicateExecutor<User> {
    List<User> findAll();
}
