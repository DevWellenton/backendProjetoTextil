package br.api.Textil.Usuario;

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
@Entity(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Size(max = 100)
    @Column(name = "NomeUsuario")
    private String nomeUsuario;

    @Size(max = 10)
    @Column(name = "SenhaUsuario")
    private String senhaUsuario;

    @Column(name = "TipoUsuario")
    private Integer tipoUsuario;

    @Size(max = 100)
    @Column(name = "EmailUsuario")
    private String emailUsuario;
}
