package br.api.Textil.Usuario.repository;

import br.api.Textil.Usuario.models.ERole;
import br.api.Textil.Usuario.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
