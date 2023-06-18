package br.api.Textil.OrdemProducao;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Terceiro.QTerceiro;
import br.api.Textil.Terceiro.Terceiro;
import br.api.Textil.Terceiro.TerceiroRepository;
import br.api.Textil.Terceiro.TerceiroRepresentation;
import br.api.Textil.exceptions.NotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    private OrdemProducaoRepository ordemProducaoRepository;
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
    @GetMapping()
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> buscarOrdensProducao(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam(name="tamanhoPagina", defaultValue = "30") int tamanhoPagina,
            @RequestParam(name = "paginaSelecionada", defaultValue = "0") int paginaSelecionada) {


        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

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

        if (ordemProducao.getEnumStatus() != EnumStatus.Ativo) {
            throw new NotFoundException("Ordem de produção não encontrada.");
        }

        OrdemProducaoRepresentation.Detalhes detalhes =
                OrdemProducaoRepresentation.Detalhes
                        .from(ordemProducao);

        return ResponseEntity.ok(detalhes);
    }
    @GetMapping("/filtro")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarOP(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "lote", required = false) String lote) {

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> filtroOrdemProducao = ordemProducaoService.buscarTodos(
                buildFiltro(status, lote).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(filtroOrdemProducao.getContent());

        if (!listaFinal.isEmpty()) {
            return ResponseEntity.ok(listaFinal);
        } else {
            throw new NotFoundException("Ordem de produção não encontrada com estes paramêtros.");
        }
    }
    @GetMapping("/filtroLote")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorLote(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam("lote") String lote) {

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> ordemProducaoLote = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.loteOp.likeIgnoreCase('%'+lote+'%').and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(ordemProducaoLote.getContent());

        if (!listaFinal.isEmpty()) {
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Ordem de produção não encontrada com este lote.");
        }
    }
    @GetMapping("/filtroTerceiro")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorTerceiro(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam("opPorTerceiro") Long terceiro) {

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

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
    @GetMapping("/filtroNomeTerceiro")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorNome(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam("NomeTerceiro") String nome) {

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

//      Optional<Terceiro> nomeFind = terceiroRepository.findOne(QTerceiro.terceiro.razaoSocial.eq(nome));

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> nomeTerceiro = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.terceiro.razaoSocial.likeIgnoreCase('%'+nome+'%').and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(nomeTerceiro.getContent());

        if (!listaFinal.isEmpty()){
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Terceiro não encontrado com este razão social.");
        }
    }
    @GetMapping("/filtroSituacao")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorStatus(
            @QuerydslPredicate(root = OrdemProducao.class) Predicate filtroURI,
            @RequestParam("StatusOp") String status) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<OrdemProducao> statusOrdemProducao = ordemProducaoService.buscarTodos(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.valueOf(String.valueOf(status))).and(filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(statusOrdemProducao.getContent());

        if (!listaFinal.isEmpty()){
            return ResponseEntity.ok(listaFinal);
        }else{
            throw new NotFoundException("Ordem de produção não encontrado com este status.");
        }
    }
    @GetMapping("/filtroDataFinal")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarPorDataFinal(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam("dataInicial") String dataInicial,
            @RequestParam("dataFinal") String dataFinal) {

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

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
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam("dataInicial") String dataInicial,
            @RequestParam("dataFinal") String dataFinal) {

        LocalDateTime dataInicialConvert = LocalDateTime.parse(dataInicial);
        LocalDateTime dataFinalConvert = LocalDateTime.parse(dataFinal);

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

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
    private BooleanExpression buildFiltro(
            String status, String lote) {

        BooleanExpression filtro = null;

        if (status != null && !status.isEmpty()) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.statusOrdemProducao.eq(StatusOrdemProducao.valueOf(status)));
        }

        if (lote != null && !lote.isEmpty()) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.loteOp.eq(lote));
        }

        return filtro;
    }
}
