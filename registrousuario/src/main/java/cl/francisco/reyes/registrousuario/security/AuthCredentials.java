package cl.francisco.reyes.registrousuario.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String pass;
}
