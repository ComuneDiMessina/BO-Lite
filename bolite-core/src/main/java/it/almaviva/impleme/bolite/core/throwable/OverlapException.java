package it.almaviva.impleme.bolite.core.throwable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class OverlapException extends RuntimeException {

    public OverlapException(String errorMessage) {
        super(errorMessage);
    }
}
