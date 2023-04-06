package br.api.Textil.Terceiro;

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
public class TerceiroService {

    private  TerceiroRepository terceiroRepository;

    public Terceiro criarTerceiro(TerceiroRepresentation.CriarOuAtualizar criar){

        return this.terceiroRepository.save(Terceiro.builder()
                .nomeTerceiro(criar.getNomeTerceiro())
                .cnpjCpfTerceiro(criar.getCnpjCpfTerceiro())
                .cepTerceiro(criar.getCepTerceiro())
                .bairroTerceiro(criar.getBairroTerceiro())
                .numeroTerceiro(criar.getNumeroTerceiro())
                .statusTerceiro(criar.getStatusTerceiro())
                .telefoneTerceiro(criar.getTelefoneTerceiro())
                .contatoTerceiro(criar.getContatoTerceiro())
                .build());
    }
    public Page<Terceiro> buscarTodos(Pageable pageable) {
        return this.terceiroRepository.findAll(pageable);
    }

    public Page<Terceiro> buscarTodos(Predicate filtroURI, Pageable pageable) {
        return this.terceiroRepository.findAll(filtroURI, pageable);
    }
    public Terceiro atualizar(
            Long idTerceiro,
            TerceiroRepresentation.CriarOuAtualizar atualizar) {

        this.getTerceiro(idTerceiro);

        Terceiro terceiroParaAtualizar = Terceiro.builder()
                .idTerceiro(idTerceiro)
                .nomeTerceiro(atualizar.getNomeTerceiro())
                .cnpjCpfTerceiro(atualizar.getCnpjCpfTerceiro())
                .cepTerceiro(atualizar.getCepTerceiro())
                .bairroTerceiro(atualizar.getBairroTerceiro())
                .numeroTerceiro(atualizar.getNumeroTerceiro())
                .statusTerceiro(atualizar.getStatusTerceiro())
                .telefoneTerceiro(atualizar.getTelefoneTerceiro())
                .contatoTerceiro(atualizar.getContatoTerceiro())
                .build();

        return this.terceiroRepository.save(terceiroParaAtualizar);
    }
    public Terceiro buscarUmTerceiro (Long idTerceiro){
        return this.getTerceiro(idTerceiro);
    }

    private Terceiro getTerceiro (Long idTerceiro){
        Optional<Terceiro> terceiroAtual =
                this.terceiroRepository.findById(idTerceiro);

        if (terceiroAtual.isPresent()) {
            return terceiroAtual.get();
        } else {
            throw new NotFoundException("Terceiro não encontrado");
        }
    }
}