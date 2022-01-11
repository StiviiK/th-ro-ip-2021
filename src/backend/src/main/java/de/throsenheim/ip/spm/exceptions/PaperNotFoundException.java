package de.throsenheim.ip.spm.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when paper is not found.
 *
 * @author Alessandro Soro
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaperNotFoundException extends RuntimeException{
    public PaperNotFoundException(String message){
        super(message);
    }

}
