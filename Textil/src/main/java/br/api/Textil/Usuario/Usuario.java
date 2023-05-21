package br.api.Textil.Usuario;

import br.api.Textil.Enum.EnumStatus;
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
}
