package br.api.Textil.OrdemProducao;

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
@RequestMapping("api/ordemProducao")
@CrossOrigin("*")
@AllArgsConstructor
public class OrdemProducaoController {

    private OrdemProducaoService ordemProducaoService;

    @PostMapping("/")
    public ResponseEntity<OrdemProducaoRepresentation.Detalhes> createOrdemProducao(
            @RequestBody @Valid OrdemProducaoRepresentation.CriarOuAtualizar criar){

        OrdemProducao ordemProducao = this.ordemProducaoService.criarOrdemProducao(criar);

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes.from(ordemProducao);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> buscarOrdensProducao(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        Page<OrdemProducao> ordemProducaoList = Objects.isNull(filtroURI) ?
                this.ordemProducaoService.buscarTodos(pageable) :
                this.ordemProducaoService.buscarTodos(filtroURI, pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoList.getContent());

        return ResponseEntity.ok(listaFinal);
    }

    @PutMapping("/{idOrdemProducao}")
    public ResponseEntity<OrdemProducaoRepresentation.Detalhes> atualizarOrdemProducao(
            @PathVariable Long idOrdemProducao,
            @RequestBody OrdemProducaoRepresentation.CriarOuAtualizar atualizar) {

        OrdemProducao ordemProducaoAtualizado =
                this.ordemProducaoService.atualizar(idOrdemProducao, atualizar);

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes
                        .from(ordemProducaoAtualizado);

        return ResponseEntity.ok(detalhes);
    }

    @GetMapping("/{idOrdemProducao}")
    public ResponseEntity<OrdemProducaoRepresentation.Detalhes> buscarUmaOrdemProducao(
            @PathVariable Long idOrdemProducao) {

        OrdemProducao ordemProducao = this.ordemProducaoService.buscarUmaOrdemProducao(idOrdemProducao);

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes
                        .from(ordemProducao);

        return ResponseEntity.ok(detalhes);
    }
}
