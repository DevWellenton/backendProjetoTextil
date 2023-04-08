package br.api.Textil.Usuario;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public interface UsuarioRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo nome não pode ser nulo")
        @NotEmpty(message = "O campo nome não pode ser vazio")
        private String nomeUsuario;

        @NotNull(message = "O campo senha não pode ser nulo")
        @NotEmpty(message = "O campo senha não pode ser vazio")
        private String senhaUsuario;

        @NotNull(message = "O campo tipo não pode ser nulo")
        private Integer tipoUsuario;

        @NotNull(message = "O campo email não pode ser nulo")
        @NotEmpty(message = "O campo email não pode ser vazio")
        private String emailUsuario;

        public static UsuarioRepresentation.Detalhes from(Usuario usuario) {
            return UsuarioRepresentation.Detalhes.builder()
                    .id(usuario.getIdUsuario())
                    .nomeUsuario(usuario.getNomeUsuario())
                    .senhaUsuario(usuario.getSenhaUsuario())
                    .tipoUsuario(usuario.getTipoUsuario())
                    .emailUsuario(usuario.getEmailUsuario())
                    .build();
        }
    }
    @Data
    @Builder
    class Detalhes {
        private Long id;
        private String nomeUsuario;
        private String senhaUsuario;
        private Integer tipoUsuario;
        private String emailUsuario;

        public static UsuarioRepresentation.Detalhes from(Usuario usuario) {
            return UsuarioRepresentation.Detalhes.builder()
                    .id(usuario.getIdUsuario())
                    .nomeUsuario(usuario.getNomeUsuario())
                    .senhaUsuario(usuario.getSenhaUsuario())
                    .tipoUsuario(usuario.getTipoUsuario())
                    .emailUsuario(usuario.getEmailUsuario())
                    .build();
        }
    }
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long id;
        private String nomeUsuario;
        private String senhaUsuario;
        private Integer tipoUsuario;
        private String emailUsuario;

        private static Lista from(Usuario usuario) {
            return Lista.builder()
                    .id(usuario.getIdUsuario())
                    .nomeUsuario(usuario.getNomeUsuario())
                    .senhaUsuario(usuario.getSenhaUsuario())
                    .tipoUsuario(usuario.getTipoUsuario())
                    .emailUsuario(usuario.getEmailUsuario())
                    .build();
        }
        public static List<Lista> from(List<Usuario> usuarioList){
            return usuarioList
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
