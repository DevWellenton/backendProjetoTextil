package br.api.Textil.OrdemProducao;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Terceiro.Terceiro;
import br.api.Textil.Usuario.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "OrdemProducao")
public class OrdemProducao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdemProducao;

    @Column(name = "dataInicialOp")
    private LocalDateTime dataInicialOp;

    @Column(name = "dataFinalOp")
    private LocalDateTime dataFinalOp;

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

    @Column(name = "situacaoOp")
    @Enumerated(value = EnumType.STRING)
    private EnumStatus enumStatus;

    @ManyToOne
    @JoinColumn(name = "idUsuario_fk", referencedColumnName = "id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "idTerceiro_fk", referencedColumnName = "idTerceiro")
    private Terceiro terceiro;
}
