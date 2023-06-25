package br.api.Textil.Terceiro;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/terceiro")
@CrossOrigin("*")
@AllArgsConstructor
public class TerceiroController {

    private TerceiroService terceiroService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TerceiroRepresentation.Detalhes> createTerceiro(
            @RequestBody @Valid TerceiroRepresentation.CriarOuAtualizar criar){

        Terceiro terceiro = this.terceiroService.criarTerceiro(criar);

        TerceiroRepresentation.Detalhes detalhes =
                TerceiroRepresentation.Detalhes.from(terceiro);

        return ResponseEntity.ok(detalhes);
    }
    @PutMapping("/{idTerceiro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TerceiroRepresentation.Detalhes> atualizarTerceiro(
            @PathVariable Long idTerceiro,
            @RequestBody TerceiroRepresentation.CriarOuAtualizar atualizar) {

        Terceiro terceiroAtualizado =
                this.terceiroService.atualizar(idTerceiro, atualizar);

        TerceiroRepresentation.Detalhes detalhes =
                TerceiroRepresentation.Detalhes
                        .from(terceiroAtualizado);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TerceiroRepresentation.Lista>> buscarTerceiro(
            @QuerydslPredicate(root = Terceiro.class) BooleanBuilder filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        filtroURI = filtroURI.and(QTerceiro.terceiro.enumStatus.eq(EnumStatus.Ativo));

        Page<Terceiro> terceiroList = Objects.isNull(filtroURI) ?
                this.terceiroService.buscarTodos(pageable) :
                this.terceiroService.buscarTodos(filtroURI, pageable);

        List<TerceiroRepresentation.Lista> listaFinal =
                TerceiroRepresentation.Lista.from(terceiroList.getContent());

        return ResponseEntity.ok(listaFinal);
    }
    @GetMapping("{idTerceiro}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TerceiroRepresentation.Detalhes> buscarUmTerceiro(
            @PathVariable Long idTerceiro) {

        Terceiro terceiro = this.terceiroService.buscarUmTerceiro(idTerceiro);

        if (terceiro.getEnumStatus() != EnumStatus.Ativo) {
            throw new NotFoundException("Terceiro não encontrado.");
        }

        TerceiroRepresentation.Detalhes detalhes =
                TerceiroRepresentation.Detalhes
                        .from(terceiro);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/filtroNome")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TerceiroRepresentation.Lista>> filtrarPorNome(
            @QuerydslPredicate(root = Terceiro.class) BooleanBuilder filtroURI,
            @RequestParam("NomeTerceiro") String nome) {

        filtroURI = filtroURI.and(QTerceiro.terceiro.enumStatus.eq(EnumStatus.Ativo));

//      Optional<Terceiro> nomeFind = terceiroRepository.findOne(QTerceiro.terceiro.razaoSocial.eq(nome));

        Pageable pageable = PageRequest.of(0, 20);
        Page<Terceiro> nomeTerceiro = terceiroService.buscarTodos(QTerceiro.terceiro.razaoSocial.likeIgnoreCase('%'+nome+'%').and(filtroURI), pageable);

        List<TerceiroRepresentation.Lista> listaFinal =
            TerceiroRepresentation.Lista.from(nomeTerceiro.getContent());

        if (!listaFinal.isEmpty()){
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Terceiro não encontrado com este razão social.");
        }
    }
    @GetMapping("/filtroStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TerceiroRepresentation.Lista>> filtrarPorStatus(
            @QuerydslPredicate(root = Terceiro.class) Predicate filtroURI,
            @RequestParam("StatusTerceiro") String status) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<Terceiro> statusTerceiro = terceiroService.buscarTodos(QTerceiro.terceiro.enumStatus.eq(EnumStatus.valueOf(String.valueOf(status))).and(filtroURI), pageable);

        List<TerceiroRepresentation.Lista> listaFinal =
                TerceiroRepresentation.Lista.from(statusTerceiro.getContent());

        if (!listaFinal.isEmpty()){
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Terceiro não encontrado com este status.");
        }
    }
}
