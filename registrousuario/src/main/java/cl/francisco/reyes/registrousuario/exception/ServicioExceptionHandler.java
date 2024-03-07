package cl.francisco.reyes.registrousuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServicioExceptionHandler {
    
    @ExceptionHandler(ValidException.class)
    public final ResponseEntity<MensajeError> validError(ValidException re) {
        MensajeError resp = new MensajeError();

        resp.setMensaje(re.getMensaje());
        return new ResponseEntity<MensajeError>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public final ResponseEntity<MensajeError> emailError(ValidException re) {
        MensajeError resp = new MensajeError();

        resp.setMensaje(re.getMensaje());
        return new ResponseEntity<MensajeError>(resp, HttpStatus.OK);
    }
}
