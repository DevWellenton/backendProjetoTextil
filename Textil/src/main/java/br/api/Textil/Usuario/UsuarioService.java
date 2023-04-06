package br.api.Textil.Usuario;

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

    public Usuario criarUsuario(UsuarioRepresentation.CriarOuAtualizar criar){

        return this.usuarioRepository.save(Usuario.builder()
                .nomeUsuario(criar.getNomeUsuario())
                .senhaUsuario(criar.getSenhaUsuario())
                .tipoUsuario(criar.getTipoUsuario())
                .emailUsuario(criar.getEmailUsuario())
                .build());
    }
    public Page<Usuario> buscarTodos(Pageable pageable) {
        return this.usuarioRepository.findAll(pageable);
    }

    public Page<Usuario> buscarTodos(Predicate filtroURI, Pageable pageable) {
        return this.usuarioRepository.findAll(filtroURI, pageable);
    }
    public Usuario atualizar(
            Long idUsuario,
            UsuarioRepresentation.CriarOuAtualizar atualizar) {

        this.getUsuario(idUsuario);

        Usuario usuarioParaAtualizar = Usuario.builder()
                .idUsuario(idUsuario)
                .nomeUsuario(atualizar.getNomeUsuario())
                .senhaUsuario(atualizar.getSenhaUsuario())
                .tipoUsuario(atualizar.getTipoUsuario())
                .emailUsuario(atualizar.getEmailUsuario())
                .build();

        return this.usuarioRepository.save(usuarioParaAtualizar);
    }
    public Usuario buscarUmUsuario (Long idUsuario){
        return this.getUsuario(idUsuario);
    }

    private Usuario getUsuario (Long idUsuario){
        Optional<Usuario> usuarioAtual =
                this.usuarioRepository.findById(idUsuario);

        if (usuarioAtual.isPresent()) {
            return usuarioAtual.get();
        } else {
            throw new NotFoundException("Usuario não encontrado");
        }
    }
}
