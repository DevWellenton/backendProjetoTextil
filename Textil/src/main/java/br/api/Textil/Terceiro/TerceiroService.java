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
                .razaoSocial(criar.getRazaoSocial())
                .cnpjTerceiro(criar.getCnpjTerceiro())
                .cepTerceiro(criar.getCepTerceiro())
                .bairroTerceiro(criar.getBairroTerceiro())
                .numeroTerceiro(criar.getNumeroTerceiro())
                .enumStatus(criar.getEnumStatus())
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
                .razaoSocial(atualizar.getRazaoSocial())
                .cnpjTerceiro(atualizar.getCnpjTerceiro())
                .cepTerceiro(atualizar.getCepTerceiro())
                .bairroTerceiro(atualizar.getBairroTerceiro())
                .numeroTerceiro(atualizar.getNumeroTerceiro())
                .enumStatus(atualizar.getEnumStatus())
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