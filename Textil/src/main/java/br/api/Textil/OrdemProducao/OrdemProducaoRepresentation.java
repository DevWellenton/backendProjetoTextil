package br.api.Textil.OrdemProducao;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface OrdemProducaoRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo data inicial não pode ser nulo")
        @NotEmpty(message = "O campo data inicial não pode ser vazio")
        private Date dataInicialOp;

        @NotNull(message = "O campo status não pode ser nulo")
        @NotEmpty(message = "O campo status não pode ser vazio")
        private Integer statusOp;

        @NotNull(message = "O campo quantidade de peças não pode ser nulo")
        @NotEmpty(message = "O campo quantidade de peças não pode ser vazio")
        private Integer qtdePecasOp;

        @NotNull(message = "O campo lote não pode ser nulo")
        @NotEmpty(message = "O campo lote não pode ser vazio")
        private String loteOp;

        public static Detalhes from(OrdemProducao ordemProducao) {
            return Detalhes.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOp(ordemProducao.getStatusOp())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
                    .build();
        }
    }

    @Data
    @Builder
    class Detalhes {
        private Long id;
        private Date dataInicialOp;
        private Date dataFinalOp;
        private Integer statusOp;
        private Integer qtdePecasOp;
        private String loteOp;
        private String obsOp;

        public static Detalhes from(OrdemProducao ordemProducao){
            return Detalhes.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOp(ordemProducao.getStatusOp())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
                    .build();
        }
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long id;
        private Date dataInicialOp;
        private Date dataFinalOp;
        private Integer statusOp;
        private Integer qtdePecasOp;
        private String loteOp;
        private String obsOp;

        private static Lista from(OrdemProducao ordemProducao){
            return Lista.builder()
                    .id(ordemProducao.getIdOrdemProducao())
                    .dataInicialOp(ordemProducao.getDataInicialOp())
                    .dataFinalOp(ordemProducao.getDataFinalOp())
                    .statusOp(ordemProducao.getStatusOp())
                    .qtdePecasOp(ordemProducao.getQtdePecasOp())
                    .loteOp(ordemProducao.getLoteOp())
                    .obsOp(ordemProducao.getObsOp())
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
