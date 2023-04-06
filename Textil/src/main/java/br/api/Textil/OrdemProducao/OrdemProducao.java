package br.api.Textil.OrdemProducao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ordemProducao")
public class OrdemProducao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdemProducao;

    @Column(name = "dataInicialOp")
    private Date dataInicialOp;

    @Column(name = "dataFinalOp")
    private Date dataFinalOp;

    @Column(name = "statusOp")
    @Enumerated(value = EnumType.STRING)
    private StatusOrdemProducao statusOrdemProducao;

    @Column(name = "qtdePecasOp")
    private Integer qtdePecasOp;

    @Size(max = 20)
    @Column(name = "loteOp")
    private String loteOp;

    @Size(max = 255)
    @Column(name = "ObsOp")
    private String obsOp;
}
