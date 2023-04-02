package br.api.Textil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrdemProducaoServiceException extends RuntimeException{

    public OrdemProducaoServiceException() {super();}

    public OrdemProducaoServiceException(final String message) {super(message);}

    public OrdemProducaoServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrdemProducaoServiceException(final Throwable cause) {
        super(cause);
    }
}
