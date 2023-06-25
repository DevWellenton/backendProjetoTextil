package br.api.Textil.OrdemProducao;

import br.api.Textil.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrdemProducaoService {

    private OrdemProducaoRepository ordemProducaoRepository;

    public OrdemProducao criarOrdemProducao(OrdemProducaoRepresentation.CriarOuAtualizar criar){

        return this.ordemProducaoRepository.save(OrdemProducao.builder()
                .dataInicialOp(criar.getDataInicialOp())
                .dataFinalOp(criar.getDataFinalOp())
                .statusOrdemProducao(criar.getStatusOrdemProducao())
                .qtdePecasOp(criar.getQtdePecasOp())
                .loteOp(criar.getLoteOp())
                .obsOp(criar.getObsOp())
                .enumStatus(criar.getEnumStatus())
                .terceiro(criar.getTerceiro())
                .usuario(criar.getUsuario())
                .build());
    }
    public Page<OrdemProducao> buscarTodos(Pageable pageable) {
        return this.ordemProducaoRepository.findAll(pageable);
    }

    public Page<OrdemProducao> buscarTodos(Predicate filtroURI, Pageable pageable) {
        return this.ordemProducaoRepository.findAll(filtroURI, pageable);
    }

//    public OrdemProducao atualizar(
//            Long idOrdemProducao,
//            OrdemProducaoRepresentation.CriarOuAtualizar atualizar) {
//
//        this.getOrdemProducao(idOrdemProducao);
//
//        OrdemProducao ordemProducaoParaAtualizar = OrdemProducao.builder()
//                .idOrdemProducao(idOrdemProducao)
//                .dataInicialOp(atualizar.getDataInicialOp())
//                .dataFinalOp(atualizar.getDataFinalOp())
//                .statusOrdemProducao(atualizar.getStatusOrdemProducao())
//                .qtdePecasOp(atualizar.getQtdePecasOp())
//                .loteOp(atualizar.getLoteOp())
//                .obsOp(atualizar.getObsOp())
//                .enumStatus(atualizar.getEnumStatus())
//                .terceiro(atualizar.getTerceiro())
//                .usuario(atualizar.getUsuario())
//                .build();
//
//        return this.ordemProducaoRepository.save(ordemProducaoParaAtualizar);
//    }
    public OrdemProducao atualizar(
            Long idOrdemProducao,
            OrdemProducaoRepresentation.CriarOuAtualizar atualizar) {

        OrdemProducao ordemProducaoExistente = this.getOrdemProducao(idOrdemProducao);

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            // Usuário é ADMIN, pode atualizar todos os campos da ordem de produção
            ordemProducaoExistente.setDataInicialOp(atualizar.getDataInicialOp());
            ordemProducaoExistente.setDataFinalOp(atualizar.getDataFinalOp());
            ordemProducaoExistente.setStatusOrdemProducao(atualizar.getStatusOrdemProducao());
            ordemProducaoExistente.setQtdePecasOp(atualizar.getQtdePecasOp());
            ordemProducaoExistente.setLoteOp(atualizar.getLoteOp());
            ordemProducaoExistente.setObsOp(atualizar.getObsOp());
            ordemProducaoExistente.setEnumStatus(atualizar.getEnumStatus());
            ordemProducaoExistente.setTerceiro(atualizar.getTerceiro());
            ordemProducaoExistente.setUsuario(atualizar.getUsuario());
        } else {
            // Usuário não é ADMIN, pode apenas atualizar o status da ordem de produção
            ordemProducaoExistente.setStatusOrdemProducao(atualizar.getStatusOrdemProducao());
        }

        return this.ordemProducaoRepository.save(ordemProducaoExistente);
    }

    public OrdemProducao buscarUmaOrdemProducao (Long idOrdemProducao){
        return this.getOrdemProducao(idOrdemProducao);
    }

    private OrdemProducao getOrdemProducao (Long idOrdemProducao){
        Optional<OrdemProducao> ordemProducaoAtual =
                this.ordemProducaoRepository.findById(idOrdemProducao);

        if (ordemProducaoAtual.isPresent()) {
            return ordemProducaoAtual.get();
        } else {
            throw new NotFoundException("Ordem de produção não encontrada");
        }
    }
}