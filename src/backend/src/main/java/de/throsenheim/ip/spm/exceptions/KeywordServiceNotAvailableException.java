package de.throsenheim.ip.spm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/***
 * Thrown, when the KeywordsService is not reachable.
 *
 * @author Lukas Metzner
 */
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class KeywordServiceNotAvailableException extends RuntimeException {
    public KeywordServiceNotAvailableException(String message) {
        super(message);
    }
}
