package cl.francisco.reyes.registrousuario.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmailException extends RuntimeException{
    private final String mensaje;
}
