package uit.ensak.dishwishbackend.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uit.ensak.dishwishbackend.exception.*;

import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileExtensionException.class)
    public ResponseEntity<String> handleInvalidFileExtensionException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Internal Server Error", null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VerificationTokenNotFoundException.class)
    public ResponseEntity<String> handleVerificationTokenNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommandNotFoundException.class)
    public ResponseEntity<String> handleCommandNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<String> NullFieldException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), null, HttpStatus.NO_CONTENT);
    }
}
