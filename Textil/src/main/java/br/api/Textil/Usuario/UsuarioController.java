package br.api.Textil.Usuario;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.exceptions.NotFoundException;
import com.querydsl.core.BooleanBuilder;
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
    @GetMapping("/")
    public ResponseEntity<List<UsuarioRepresentation.Lista>> buscarUsuario(
            @QuerydslPredicate(root = Usuario.class) BooleanBuilder filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        filtroURI = filtroURI.and(QUsuario.usuario.enumStatus.eq(EnumStatus.Ativo));

        Page<Usuario> usuarioList = Objects.isNull(filtroURI) ?
                this.usuarioService.buscarTodos(pageable) :
                this.usuarioService.buscarTodos(filtroURI, pageable);

        List<UsuarioRepresentation.Lista> listaFinal =
                UsuarioRepresentation.Lista.from(usuarioList.getContent());

        return ResponseEntity.ok(listaFinal);
    }
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioRepresentation.Detalhes> buscarUmUsuario(
            @PathVariable Long idUsuario) {

        Usuario usuario = this.usuarioService.buscarUmUsuario(idUsuario);

        if (usuario.getEnumStatus() != EnumStatus.Ativo){
            throw new NotFoundException("Usuario não encontrado.");
        }

        UsuarioRepresentation.Detalhes detalhes =
                UsuarioRepresentation.Detalhes
                        .from(usuario);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/filtroStatus")
    public ResponseEntity<List<UsuarioRepresentation.Lista>> filtrarPorStatus(
            @QuerydslPredicate(root = Usuario.class) Predicate filtroURI,
            @RequestParam("StatusUsuario") String status) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<Usuario> statusUsuario = usuarioService.buscarTodos(QUsuario.usuario.enumStatus.eq(EnumStatus.valueOf(String.valueOf(status))).and(filtroURI), pageable);

        List<UsuarioRepresentation.Lista> listaFinal =
                UsuarioRepresentation.Lista.from(statusUsuario.getContent());

        if (!listaFinal.isEmpty()){
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Usuario não encontrado com este status.");
        }
    }
}
