package br.api.Textil.Usuario;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.OrdemProducao.OrdemProducao;
import br.api.Textil.Terceiro.Terceiro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Size(max = 100)
    @Column(name = "nomeUsuario")
    private String nomeUsuario;

    @Size(max = 10)
    @Column(name = "senhaUsuario")
    private String senhaUsuario;

    @Column(name = "tipoUsuario")
    private Integer tipoUsuario;

    @Size(max = 100)
    @Column(name = "emailUsuario")
    private String emailUsuario;

    @Column(name = "statusUsuario")
    @Enumerated(value = EnumType.STRING)
    private EnumStatus enumStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", orphanRemoval = true)
    List<OrdemProducao> OrdemProducao = new ArrayList<>();

    @OneToOne(mappedBy = "usuario", fetch = FetchType.LAZY, orphanRemoval = true)
    private Terceiro terceiro;
}
