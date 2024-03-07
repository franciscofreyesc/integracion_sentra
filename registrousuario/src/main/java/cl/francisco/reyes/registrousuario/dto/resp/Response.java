package cl.francisco.reyes.registrousuario.dto.resp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response {
    String id;
    String created;
    String modified;
    String last_login;
    String token;
    String isActive;
}
