package br.api.Textil.OrdemProducao;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Terceiro.TerceiroRepository;
import br.api.Textil.Usuario.models.ERole;
import br.api.Textil.Usuario.models.Role;
import br.api.Textil.Usuario.models.User;
import br.api.Textil.Usuario.repository.UserRepository;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/ordemProducao")
@CrossOrigin("*")
@AllArgsConstructor
public class OrdemProducaoController {

    private OrdemProducaoService ordemProducaoService;

    private UserRepository userRepository;



    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
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


        SecurityContext context = SecurityContextHolder.getContext();
        User usuarioLogado = this.userRepository.findByUsername(context.getAuthentication().getName()).orElse(null);

        Pageable pageable = PageRequest.of(paginaSelecionada, tamanhoPagina);

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

        boolean role_admin = usuarioLogado.getRoles().stream().filter(role -> role.getName().equals(ERole.valueOf("ROLE_ADMIN"))).count() > 0;

        if(usuarioLogado != null && !role_admin){
            filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.terceiro.usuario().id.eq(usuarioLogado.getId()));
        }

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
    @GetMapping("/filtro")
    public ResponseEntity<List<OrdemProducaoRepresentation.Lista>> filtrarOP(
            @QuerydslPredicate(root = OrdemProducao.class) BooleanBuilder filtroURI,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "lote", required = false) String lote,
            @RequestParam(value = "dataInicialInicio", required = false) String dataInicialInicio,
            @RequestParam(value = "dataFinalInicio", required = false) String dataFinalInicio,
            @RequestParam(value = "dataInicialFinal", required = false) String dataInicialFinal,
            @RequestParam(value = "dataFinalFinal", required = false) String dataFinalFinal,
            @RequestParam(value = "opPorTerceiro", required = false) Long terceiro,
            @RequestParam(value = "opPorIdOp", required = false) Long idOp) {

        SecurityContext context = SecurityContextHolder.getContext();
        User usuarioLogado = this.userRepository.findByUsername(context.getAuthentication().getName()).orElse(null);

        filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.enumStatus.eq(EnumStatus.Ativo));

        boolean role_admin = usuarioLogado.getRoles().stream().filter(role -> role.getName().equals(ERole.valueOf("ROLE_ADMIN"))).count() > 0;

        if(usuarioLogado != null && !role_admin){
            filtroURI = filtroURI.and(QOrdemProducao.ordemProducao.terceiro.usuario().id.eq(usuarioLogado.getId()));
        }

        Pageable pageable = PageRequest.of(0, 20);

        Page<OrdemProducao> filtroOrdemProducao = ordemProducaoService.buscarTodos(
                buildFiltro(status, lote, dataInicialInicio, dataFinalInicio, dataInicialFinal, dataFinalFinal, terceiro, idOp, filtroURI), pageable);

        List<OrdemProducaoRepresentation.Lista> listaFinal =
                OrdemProducaoRepresentation.Lista.from(filtroOrdemProducao.getContent());

        return ResponseEntity.ok(listaFinal);
    }
    private BooleanBuilder buildFiltro(
            String status, String lote, String dataInicialInicio, String dataFinalInicio, String dataInicialFinal, String dataFinalFinal,
            Long terceiro , Long idOp, BooleanBuilder filtroURI) {

        BooleanBuilder filtro = filtroURI;

        int cont=0;

        if (idOp != null) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.idOrdemProducao.eq(idOp));
            cont++;
        }

        if (terceiro != null) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.terceiro.idTerceiro.eq(terceiro));
            cont++;
        }

        if (status != null && !status.isEmpty()) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.statusOrdemProducao.eq(StatusOrdemProducao.valueOf(status)));
            cont++;
        }
        if (lote != null && !lote.isEmpty()) {
            filtro = filtro.and(QOrdemProducao.ordemProducao.loteOp.likeIgnoreCase('%'+lote+'%'));
            cont++;
        }
        if ((dataInicialInicio != null && !dataFinalInicio.isEmpty())) {
            LocalDateTime dataInicialInicioConvert = LocalDateTime.parse(dataInicialInicio);
            LocalDateTime dataFinalInicioConvert = LocalDateTime.parse(dataFinalInicio);
            filtro = filtro.and(QOrdemProducao.ordemProducao.dataInicialOp.between(dataInicialInicioConvert, dataFinalInicioConvert));
            cont++;
        }
        if ((dataInicialFinal != null && !dataFinalFinal.isEmpty())) {
            LocalDateTime dataInicialFinalConvert = LocalDateTime.parse(dataInicialFinal);
            LocalDateTime dataFinalFinalConvert = LocalDateTime.parse(dataFinalFinal);
            filtro = filtro.and(QOrdemProducao.ordemProducao.dataFinalOp.between(dataInicialFinalConvert, dataFinalFinalConvert));
            cont++;
        }

        if (cont>0) {
            return filtro;
        } else {
            throw new NotFoundException("Ordem de produção não encontrada com estes paramêtros.");
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
}
