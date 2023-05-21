package br.api.Textil.OrdemProducao;

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

    public OrdemProducao atualizar(
            Long idOrdemProducao,
            OrdemProducaoRepresentation.CriarOuAtualizar atualizar) {

        this.getOrdemProducao(idOrdemProducao);

        OrdemProducao ordemProducaoParaAtualizar = OrdemProducao.builder()
                .idOrdemProducao(idOrdemProducao)
                .dataInicialOp(atualizar.getDataInicialOp())
                .dataFinalOp(atualizar.getDataFinalOp())
                .statusOrdemProducao(atualizar.getStatusOrdemProducao())
                .qtdePecasOp(atualizar.getQtdePecasOp())
                .loteOp(atualizar.getLoteOp())
                .obsOp(atualizar.getObsOp())
                .enumStatus(atualizar.getEnumStatus())
                .terceiro(atualizar.getTerceiro())
                .usuario(atualizar.getUsuario())
                .build();

        return this.ordemProducaoRepository.save(ordemProducaoParaAtualizar);
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