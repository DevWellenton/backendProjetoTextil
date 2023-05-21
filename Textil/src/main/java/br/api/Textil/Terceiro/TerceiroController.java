package br.api.Textil.Terceiro;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.OrdemProducao.OrdemProducao;
import br.api.Textil.OrdemProducao.OrdemProducaoRepresentation;
import br.api.Textil.OrdemProducao.QOrdemProducao;
import br.api.Textil.exceptions.NotFoundException;
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
import java.util.Optional;

@RestController
@RequestMapping("api/terceiro")
@CrossOrigin("*")
@AllArgsConstructor
public class TerceiroController {

    private TerceiroService terceiroService;

    private TerceiroRepository terceiroRepository;

    @PostMapping("/")
    public ResponseEntity<TerceiroRepresentation.Detalhes> createTerceiro(
            @RequestBody @Valid TerceiroRepresentation.CriarOuAtualizar criar){

        Terceiro terceiro = this.terceiroService.criarTerceiro(criar);

        TerceiroRepresentation.Detalhes detalhes =
                TerceiroRepresentation.Detalhes.from(terceiro);

        return ResponseEntity.ok(detalhes);
    }
    @PutMapping("/{idTerceiro}")
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
    @GetMapping("/")
    public ResponseEntity<List<TerceiroRepresentation.Lista>> buscarTerceiro(
            @QuerydslPredicate(root = Terceiro.class) Predicate filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        Page<Terceiro> terceiroList = Objects.isNull(filtroURI) ?
                this.terceiroService.buscarTodos(pageable) :
                this.terceiroService.buscarTodos(filtroURI, pageable);

        List<TerceiroRepresentation.Lista> listaFinal =
                TerceiroRepresentation.Lista.from(terceiroList.getContent());

        return ResponseEntity.ok(listaFinal);
    }
    @GetMapping("/{idTerceiro}")
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
    @GetMapping("/Terceiro/{idTerceiro}")
    public ResponseEntity<TerceiroRepresentation.Detalhes> buscarUmTerceiroInativo(
            @PathVariable Long idTerceiro) {

        Terceiro terceiro = this.terceiroService.buscarUmTerceiro(idTerceiro);

        if (terceiro.getEnumStatus() != EnumStatus.Inativo) {
            throw new NotFoundException("Terceiro não encontrado.");
        }

        TerceiroRepresentation.Detalhes detalhes =
                TerceiroRepresentation.Detalhes
                        .from(terceiro);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/filtroNomeTerceiro")
    public ResponseEntity<List<TerceiroRepresentation.Lista>> filtrarPorNome(
            @QuerydslPredicate(root = Terceiro.class) Predicate filtroURI,
            @RequestParam("NomeTerceiro") String nome) {

        Optional<Terceiro> nomeFind = terceiroRepository.findOne(QTerceiro.terceiro.razaoSocial.eq(nome));

        if (nomeFind.isPresent()){
            Pageable pageable = PageRequest.of(0, 20);
            Page<Terceiro> nomeTerceiro = terceiroService.buscarTodos(QTerceiro.terceiro.eq(nomeFind.get()).and(filtroURI), pageable);


            List<TerceiroRepresentation.Lista> listaFinal =
                    TerceiroRepresentation.Lista.from(nomeTerceiro.getContent());

            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Terceiro não encontrado.");
        }
    }
}
