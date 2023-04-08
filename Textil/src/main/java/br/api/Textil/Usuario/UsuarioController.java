package br.api.Textil.Usuario;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/usuario")
@CrossOrigin("*")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping("/")
    public ResponseEntity<UsuarioRepresentation.Detalhes> createUsuario(
            @RequestBody @Valid UsuarioRepresentation.CriarOuAtualizar criar){

        Usuario usuario = this.usuarioService.criarUsuario(criar);

        UsuarioRepresentation.Detalhes detalhes =
                UsuarioRepresentation.Detalhes.from(usuario);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/")
    public ResponseEntity<List<UsuarioRepresentation.Lista>> buscarUsuario(
            @QuerydslPredicate(root = Usuario.class) Predicate filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        Page<Usuario> usuarioList = Objects.isNull(filtroURI) ?
                this.usuarioService.buscarTodos(pageable) :
                this.usuarioService.buscarTodos(filtroURI, pageable);

        List<UsuarioRepresentation.Lista> listaFinal =
                UsuarioRepresentation.Lista.from(usuarioList.getContent());

        return ResponseEntity.ok(listaFinal);
    }
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioRepresentation.Detalhes> atualizarUsuario(
            @PathVariable Long idUsuario,
            @RequestBody UsuarioRepresentation.CriarOuAtualizar atualizar) {

        Usuario usuarioAtualizado =
                this.usuarioService.atualizar(idUsuario, atualizar);

        UsuarioRepresentation.Detalhes detalhes =
                UsuarioRepresentation.Detalhes
                        .from(usuarioAtualizado);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioRepresentation.Detalhes> buscarUmUsuario(
            @PathVariable Long idUsuario) {

        Usuario usuario = this.usuarioService.buscarUmUsuario(idUsuario);

        UsuarioRepresentation.Detalhes detalhes =
                UsuarioRepresentation.Detalhes
                        .from(usuario);

        return ResponseEntity.ok(detalhes);
    }
}
