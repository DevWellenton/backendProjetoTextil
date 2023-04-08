package br.api.Textil.Terceiro;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public interface TerceiroRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo nome não pode ser nulo")
        @NotEmpty(message = "O campo nome não pode ser vazio")
        private String nomeTerceiro;

        @NotNull(message = "O campo Cnpj/Cpf não pode ser nulo")
        @NotEmpty(message = "O campo Cnpj/Cpf não pode ser vazio")
        private String cnpjCpfTerceiro;

        @NotNull(message = "O campo endereço não pode ser nulo")
        @NotEmpty(message = "O campo endereço não pode ser vazio")
        private String enderecoTerceiro;

        @NotNull(message = "O campo cep não pode ser nulo")
        @NotEmpty(message = "O campo cep não pode ser vazio")
        private String cepTerceiro;

        @NotNull(message = "O campo bairro não pode ser nulo")
        @NotEmpty(message = "O campo bairro não pode ser vazio")
        private String bairroTerceiro;

        @NotNull(message = "O campo número não pode ser nulo")
        @NotEmpty(message = "O campo número não pode ser vazio")
        private String numeroTerceiro;

        @NotNull(message = "O campo status não pode ser nulo")
        @NotEmpty(message = "O campo status não pode ser vazio")
        private StatusTerceiro statusTerceiro;

        private String telefoneTerceiro;

        private String contatoTerceiro;

        public static Detalhes from(Terceiro terceiro) {
            return Detalhes.builder()
                    .id(terceiro.getIdTerceiro())
                    .nomeTerceiro(terceiro.getNomeTerceiro())
                    .cnpjCpfTerceiro(terceiro.getCnpjCpfTerceiro())
                    .enderecoTerceiro(terceiro.getEnderecoTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .statusTerceiro(terceiro.getStatusTerceiro())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .build();
        }
    }
    @Data
    @Builder
    class Detalhes {
        private Long id;
        private String nomeTerceiro;
        private String cnpjCpfTerceiro;
        private String enderecoTerceiro;
        private String cepTerceiro;
        private String bairroTerceiro;
        private String numeroTerceiro;
        private StatusTerceiro statusTerceiro;
        private String telefoneTerceiro;
        private String contatoTerceiro;

        public static TerceiroRepresentation.Detalhes from(Terceiro terceiro){
            return Detalhes.builder()
                    .id(terceiro.getIdTerceiro())
                    .nomeTerceiro(terceiro.getNomeTerceiro())
                    .cnpjCpfTerceiro(terceiro.getCnpjCpfTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .statusTerceiro(terceiro.getStatusTerceiro())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .build();
        }
    }
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long id;
        private String nomeTerceiro;
        private String cnpjCpfTerceiro;
        private String enderecoTerceiro;
        private String cepTerceiro;
        private String bairroTerceiro;
        private String numeroTerceiro;
        private StatusTerceiro statusTerceiro;
        private String telefoneTerceiro;
        private String contatoTerceiro;

        private static Lista from(Terceiro terceiro) {
            return Lista.builder()
                    .id(terceiro.getIdTerceiro())
                    .nomeTerceiro(terceiro.getNomeTerceiro())
                    .cnpjCpfTerceiro(terceiro.getCnpjCpfTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .statusTerceiro(terceiro.getStatusTerceiro())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .build();
        }
        public static List<Lista> from(List<Terceiro> terceiroList){
            return terceiroList
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
