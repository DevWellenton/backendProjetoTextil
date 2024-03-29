package br.api.Textil.Usuario;


import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Usuario.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static br.api.Textil.Enum.EnumStatus.Ativo;

public interface UsuarioRepresentation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class CriarOuAtualizar {

        @NotNull(message = "O campo nome não pode ser nulo")
        @NotEmpty(message = "O campo nome não pode ser vazio")
        private String username;

        @NotNull(message = "O campo email não pode ser nulo")
        @NotEmpty(message = "O campo email não pode ser vazio")
        private String email;

        @NotNull(message = "O campo senha não pode ser nulo")
        @NotEmpty(message = "O campo senha não pode ser vazio")
        private String password;

        @NotNull(message = "O campo status não pode ser nulo")
        private EnumStatus enumStatus = Ativo;

        public static UsuarioRepresentation.Detalhes from(User usuario) {
            return UsuarioRepresentation.Detalhes.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .enumStatus(usuario.getEnumStatus())
                    .build();
        }
    }
    @Data
    @Builder
    class Detalhes {
        private Long id;
        private String username;
        private String email;
        private String password;
        private EnumStatus enumStatus;

        public static UsuarioRepresentation.Detalhes from(User usuario) {
            return Detalhes.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .enumStatus(usuario.getEnumStatus())
                    .build();
        }
    }
    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Lista {
        private Long id;
        private String username;
        private String email;
        private String password;
        private EnumStatus enumStatus;

        private static Lista from(User usuario) {
            return Lista.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .enumStatus(usuario.getEnumStatus())
                    .build();
        }
        public static List<Lista> from(List<User> usuarioList){
            return usuarioList
                    .stream()
                    .map(Lista::from)
                    .collect(Collectors.toList());
        }
    }
}
