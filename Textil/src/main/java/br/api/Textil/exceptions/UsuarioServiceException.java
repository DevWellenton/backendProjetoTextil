package br.api.Textil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioServiceException extends RuntimeException{

    public UsuarioServiceException() {super();}

    public UsuarioServiceException(final String message) {super(message);}

    public UsuarioServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsuarioServiceException(final Throwable cause) {
        super(cause);
    }
}
