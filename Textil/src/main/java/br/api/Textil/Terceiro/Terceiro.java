package br.api.Textil.Terceiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
    private String enderecoTerceiro;

    @Size(max = 8)
    @Column(name = "CepTerceiro")
    private String cepTerceiro;

    @Size(max = 100)
    @Column(name = "BairroTerceiro")
    private String bairroTerceiro;

    @Size(max = 10)
    @Column(name = "NumeroTerceiro")
    private String numeroTerceiro;

    @Size(max = 11)
    @Column(name = "TelefoneTerceiro")
    private String telefoneTerceiro;

    @Size(max = 50)
    @Column(name = "ContatoTerceiro")
    private String contatoTerceiro;

    @Column(name = "statusTerceiro")
    @Enumerated(value = EnumType.STRING)
    private StatusTerceiro statusTerceiro;
}
