package br.api.Textil.Usuario;

import br.api.Textil.Usuario.models.User;
import br.api.Textil.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public User criarUsuario(UsuarioRepresentation.CriarOuAtualizar criar){

        return this.usuarioRepository.save(User.builder()
                .username(criar.getUserName())
                .email(criar.getEmail())
                .password(criar.getPassword())
                .tipoUsuario(criar.getTipoUsuario())
                .enumStatus(criar.getEnumStatus())
                .build());
    }
    public Page<User> buscarTodos(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    }

    public Page<User> buscarTodos(Predicate filtroURI, Pageable pageable) {
        return this.usuarioRepository.findAll(filtroURI, pageable);
    }
    public User atualizar(
            Long id,
            UsuarioRepresentation.CriarOuAtualizar atualizar) {

        this.getUsuario(id);

        User usuarioParaAtualizar = User.builder()
                .id(id)
                .username(atualizar.getUserName())
                .email(atualizar.getEmail())
                .password(atualizar.getPassword())
                .tipoUsuario(atualizar.getTipoUsuario())
                .enumStatus(atualizar.getEnumStatus())
                .build();

        return this.usuarioRepository.save(usuarioParaAtualizar);
    }
    public User buscarUmUsuario (Long id){
        return this.getUsuario(id);
    }

    private User getUsuario (Long id){
        Optional<User> usuarioAtual =
                this.usuarioRepository.findById(id);

        if (usuarioAtual.isPresent()) {
            return usuarioAtual.get();
        } else {
            throw new NotFoundException("Usuario não encontrado");
        }
    }
}
