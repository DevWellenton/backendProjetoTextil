package br.api.Textil.OrdemProducao;

import br.api.Textil.Terceiro.Terceiro;
import br.api.Textil.Terceiro.TerceiroRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/ordemProducao")
@CrossOrigin("*")
@AllArgsConstructor
public class OrdemProducaoController {

    private OrdemProducaoService ordemProducaoService;

    private TerceiroRepository terceiroRepository;

    @PostMapping()
    public ResponseEntity<OrdemProducaoRepresentation.Detalhes> createOrdemProducao(
            @RequestBody @Valid OrdemProducaoRepresentation.CriarOuAtualizar criar){

        OrdemProducao ordemProducao = this.ordemProducaoService.criarOrdemProducao(criar);

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes.from(ordemProducao);

        return ResponseEntity.ok(detalhes);
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
    @GetMapping("/{idOrdemProducao}")
    public ResponseEntity<OrdemProducaoRepresentation.Detalhes> buscarUmaOrdemProducao(
            @PathVariable Long idOrdemProducao) {

        OrdemProducao ordemProducao = this.ordemProducaoService.buscarUmaOrdemProducao(idOrdemProducao);

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes
                        .from(ordemProducao);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/filtroStatus")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorStatus(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("status") String status) {

//      Optional<OrdemProducao> statusFind = ordemProducaoRepository.findOne(QOrdemProducao.ordemProducao.statusOrdemProducao.eq(StatusOrdemProducao.valueOf(status)));

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> ordemProducaoStatus = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.statusOrdemProducao.eq(StatusOrdemProducao.valueOf(status)).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoStatus.getContent());

        if (!listaFinal.isEmpty()) {
            return ResponseEntity.ok(listaFinal);
        } else {
            throw new NotFoundException("Ordem de produção não encontrada com este status.");
        }
    }
    @GetMapping("/filtroTerceiro")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorTerceiro(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("opPorTerceiro") Long terceiro) {

        Optional<Terceiro> terceiroFind = terceiroRepository.findById(terceiro);

        if (terceiroFind.isPresent()){
            Pageable pageable = PageRequest.of(0, 20);
            Page<OrdemProducao> ordemProducaoTerceiro = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.terceiro.eq(terceiroFind.get()).and(filtroURI), pageable);


            List<OrdemProducaoRepresentation.Lista> listaFinal =
                    OrdemProducaoRepresentation.Lista.from(ordemProducaoTerceiro.getContent());

            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Ordem de produção não encontrada com este terceiro.");
        }
    }
    @GetMapping("/filtroLote")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorLote(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("lote") String lote) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> ordemProducaoLote = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.loteOp.eq(lote).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoLote.getContent());

        if (!listaFinal.isEmpty()) {
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Ordem de produção não encontrada com este lote.");
        }
    }
    @GetMapping("/filtroDataFinal")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorDataFinal(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("dataInicial") String dataInicial,
            @RequestParam("dataFinal") String dataFinal) {

        LocalDateTime dataInicialConvert = LocalDateTime.parse(dataInicial);//.atTime(LocalTime.now());
        LocalDateTime dataFinalConvert = LocalDateTime.parse(dataFinal);//.atTime(LocalTime.now());
//        System.out.println("dataInicialConvert= "+dataInicialConvert);
//        System.out.println("dataFinalConvert= "+dataFinalConvert);

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> ordemProducaoFinal = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.dataFinalOp.between(dataInicialConvert, dataFinalConvert).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoFinal.getContent());

        if (!listaFinal.isEmpty()) {
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Ordem de produção não encontrada com o parametro passado.");
        }
    }
    @GetMapping("/filtroDataInicial")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorDataInicial(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("dataInicial") String dataInicial,
            @RequestParam("dataFinal") String dataFinal) {

        LocalDateTime dataInicialConvert = LocalDateTime.parse(dataInicial);
        LocalDateTime dataFinalConvert = LocalDateTime.parse(dataFinal);

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> ordemProducaoInicial = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.dataInicialOp.between(dataInicialConvert, dataFinalConvert).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaInicial =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoInicial.getContent());

        if (!listaInicial.isEmpty()) {
            return ResponseEntity.ok(listaInicial);
        }else{
            throw new NotFoundException("Ordem de produção não encontrada com o parametro passado.");
        }
    }
}
