package br.api.Textil.Terceiro;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Usuario.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;

import static br.api.Textil.Enum.EnumStatus.Ativo;

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
    @Column(name = "razaoSocial")
    private String razaoSocial;

    @Size(max = 14)
    @Column(name = "cnpjTerceiro")
    private String cnpjTerceiro;

    @Size(max = 255)
    @Column(name = "enderecoTerceiro")
    private String enderecoTerceiro;

    @Size(max = 8)
    @Column(name = "cepTerceiro")
    private String cepTerceiro;

    @Size(max = 100)
    @Column(name = "bairroTerceiro")
    private String bairroTerceiro;

    @Size(max = 10)
    @Column(name = "numeroTerceiro")
    private String numeroTerceiro;

    @Size(max = 11)
    @Column(name = "telefoneTerceiro")
    private String telefoneTerceiro;

    @Size(max = 50)
    @Column(name = "contatoTerceiro")
    private String contatoTerceiro;

    @Column(name = "statusTerceiro")
    @Enumerated(value = EnumType.STRING)
    private EnumStatus enumStatus = Ativo;

    @OneToOne
    @JoinColumn(name = "IdUsuario_fk", referencedColumnName = "id")
    private User usuario;
}
