package br.api.Textil.OrdemProducao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ordensProducao")
public class OrdemProducao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdemProducao;

    @Column(name = "dataInicialOp")
    private Date dataInicialOp;

    @Column(name = "dataFinalOp")
    private Date dataFinalOp;

    @Column(name = "statusOp")
    private Integer statusOp;

    @Column(name = "qtdePecasOp")
    private Integer qtdePecasOp;

    @Column(name = "loteOp")
    private String loteOp;

    @Column(name = "ObsOp")
    private String ObsOp;
}
