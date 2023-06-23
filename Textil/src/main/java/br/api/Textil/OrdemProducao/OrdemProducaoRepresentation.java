package br.api.Textil.OrdemProducao;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Terceiro.Terceiro;
import br.api.Textil.Usuario.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static br.api.Textil.Enum.EnumStatus.Ativo;

public interface OrdemProducaoRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo data inicial não pode ser nulo")
        private LocalDateTime dataInicialOp;

        private LocalDateTime dataFinalOp;

        @NotNull(message = "O campo status não pode ser nulo")
        private StatusOrdemProducao statusOrdemProducao;

        @NotNull(message = "O campo quantidade de peças não pode ser nulo")
        private Integer qtdePecasOp;

        @NotNull(message = "O campo lote não pode ser nulo")
        @NotEmpty(message = "O campo lote não pode ser vazio")
        private String loteOp;

        private String obsOp;

        private EnumStatus enumStatus = Ativo;

        private User usuario;

        private Terceiro terceiro;

        public static Detalhes from(OrdemProducao ordemProducao) {
            return Detalhes.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOrdemProducao(ordemProducao.getStatusOrdemProducao())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
                    .enumStatus(ordemProducao.getEnumStatus())
                    .usuario(ordemProducao.getUsuario())
                    .terceiro(ordemProducao.getTerceiro())
                    .build();
        }
    }

    @Data
    @Builder
    class Detalhes {
        private Long id;
        private LocalDateTime dataInicialOp;
        private LocalDateTime dataFinalOp;
        private StatusOrdemProducao statusOrdemProducao;
        private Integer qtdePecasOp;
        private String loteOp;
        private String obsOp;
        private EnumStatus enumStatus;
        private User usuario;
        private Terceiro terceiro;

        public static Detalhes from(OrdemProducao ordemProducao){
            return Detalhes.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOrdemProducao(ordemProducao.getStatusOrdemProducao())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
                    .enumStatus(ordemProducao.getEnumStatus())
                    .usuario(ordemProducao.getUsuario())
                    .terceiro(ordemProducao.getTerceiro())
                    .build();
        }
    }
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long id;
        private LocalDateTime dataInicialOp;
        private LocalDateTime dataFinalOp;
        private StatusOrdemProducao statusOrdemProducao;
        private Integer qtdePecasOp;
        private String loteOp;
        private String obsOp;
        private EnumStatus enumStatus;
        private User usuario;
        private Terceiro terceiro;

        private static Lista from(OrdemProducao ordemProducao){
            return Lista.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOrdemProducao(ordemProducao.getStatusOrdemProducao())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
                    .enumStatus(ordemProducao.getEnumStatus())
                    .usuario(ordemProducao.getUsuario())
                    .terceiro(ordemProducao.getTerceiro())
                    .build();
        }

        public static List<Lista> from(List<OrdemProducao> ordemProducaoList){
            return ordemProducaoList
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
