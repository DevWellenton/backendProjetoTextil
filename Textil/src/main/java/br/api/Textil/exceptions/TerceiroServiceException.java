package br.api.Textil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TerceiroServiceException extends RuntimeException{

    public TerceiroServiceException() {super();}

    public TerceiroServiceException(final String message) {super(message);}

    public TerceiroServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TerceiroServiceException(final Throwable cause) {
        super(cause);
    }
}
