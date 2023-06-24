package br.api.Textil.Terceiro;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Usuario.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static br.api.Textil.Enum.EnumStatus.Ativo;

public interface TerceiroRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo nome não pode ser nulo")
        @NotEmpty(message = "O campo nome não pode ser vazio")
        private String razaoSocial;

        @NotNull(message = "O campo Cnpj/Cpf não pode ser nulo")
        @NotEmpty(message = "O campo Cnpj/Cpf não pode ser vazio")
        private String cnpjTerceiro;

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
        private EnumStatus enumStatus = Ativo;

        private String telefoneTerceiro;

        private String contatoTerceiro;

        private User usuario;

        public static Detalhes from(Terceiro terceiro) {
            return Detalhes.builder()
                    .idTerceiro(terceiro.getIdTerceiro())
                    .razaoSocial(terceiro.getRazaoSocial())
                    .cnpjTerceiro(terceiro.getCnpjTerceiro())
                    .enderecoTerceiro(terceiro.getEnderecoTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .enumStatus(terceiro.getEnumStatus())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .usuario(terceiro.getUsuario())
                    .build();
        }
    }
    @Data
    @Builder
    class Detalhes {
        private Long idTerceiro;
        private String razaoSocial;
        private String cnpjTerceiro;
        private String enderecoTerceiro;
        private String cepTerceiro;
        private String bairroTerceiro;
        private String numeroTerceiro;
        private EnumStatus enumStatus;
        private String telefoneTerceiro;
        private String contatoTerceiro;
        private User usuario;

        public static TerceiroRepresentation.Detalhes from(Terceiro terceiro){
            return Detalhes.builder()
                    .idTerceiro(terceiro.getIdTerceiro())
                    .razaoSocial(terceiro.getRazaoSocial())
                    .cnpjTerceiro(terceiro.getCnpjTerceiro())
                    .enderecoTerceiro(terceiro.getEnderecoTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .enumStatus(terceiro.getEnumStatus())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .usuario(terceiro.getUsuario())
                    .build();
        }
    }
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long idTerceiro;
        private String razaoSocial;
        private String cnpjTerceiro;
        private String enderecoTerceiro;
        private String cepTerceiro;
        private String bairroTerceiro;
        private String numeroTerceiro;
        private EnumStatus enumStatus;
        private String telefoneTerceiro;
        private String contatoTerceiro;
        private User usuario;

        private static Lista from(Terceiro terceiro) {
            return Lista.builder()
                    .idTerceiro(terceiro.getIdTerceiro())
                    .razaoSocial(terceiro.getRazaoSocial())
                    .cnpjTerceiro(terceiro.getCnpjTerceiro())
                    .enderecoTerceiro(terceiro.getEnderecoTerceiro())
                    .cepTerceiro(terceiro.getCepTerceiro())
                    .bairroTerceiro(terceiro.getBairroTerceiro())
                    .numeroTerceiro(terceiro.getNumeroTerceiro())
                    .enumStatus(terceiro.getEnumStatus())
                    .telefoneTerceiro(terceiro.getTelefoneTerceiro())
                    .contatoTerceiro(terceiro.getContatoTerceiro())
                    .usuario(terceiro.getUsuario())
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
