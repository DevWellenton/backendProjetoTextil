package br.api.Textil.Terceiro;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Terceiro")
public class Terceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTerceiro;

    @Size(max = 200)
    @Column(name = "NomeTerceiro")
    private String nomeTerceiro;

    @Size(max = 14)
    @Column(name = "CnpjCpfTerceiro")
    private String cnpjCpfTerceiro;

    @Size(max = 255)
    @Column(name = "EnderecoTerceiro")
    private String EnderecoTerceiro;

    @Size(max = 8)
    @Column(name = "CepTerceiro")
    private String CepTerceiro;

    @Size(max = 100)
    @Column(name = "BairroTerceiro")
    private String BairroTerceiro;

    @Size(max = 10)
    @Column(name = "NumeroTerceiro")
    private String NumeroTerceiro;

    @Size(max = 11)
    @Column(name = "TelefoneTerceiro")
    private String TelefoneTerceiro;

    @Size(max = 50)
    @Column(name = "ContatoTerceiro")
    private String ContatoTerceiro;

    @Column(name = "statusTerceiro")
    @Enumerated(value = EnumType.STRING)
    private StatusTerceiro statusTerceiro;
}
