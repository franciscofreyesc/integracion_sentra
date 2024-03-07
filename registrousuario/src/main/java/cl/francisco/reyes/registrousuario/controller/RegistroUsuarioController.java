package cl.francisco.reyes.registrousuario.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import cl.francisco.reyes.registrousuario.dto.req.Usuario;
import cl.francisco.reyes.registrousuario.dto.resp.Response;
import cl.francisco.reyes.registrousuario.exception.EmailException;
import cl.francisco.reyes.registrousuario.exception.ValidException;
import cl.francisco.reyes.registrousuario.repository.UsuariosRepository;
import cl.francisco.reyes.registrousuario.repository.entity.Usuarios;
import cl.francisco.reyes.registrousuario.service.RegistroUsuarioService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Slf4j
public class RegistroUsuarioController {

    @Autowired
    RegistroUsuarioService registroUsuarioService;
    
    @Value("${registrousuario.formatopassword}")
    private String pass;

    @Autowired
    UsuariosRepository usuariosRepository;
    
    @PostMapping(path = "/registroUsuario", produces = "application/json")
    public ResponseEntity<Response> registroUsuario(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody Usuario req) throws SQLException {

        String validEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        log.info("Validando reglas");
        if (req == null) {
            throw new ValidException("Body no puede ser nulo.");
        }

        if (!req.getEmail().matches(validEmail)) {
            throw new ValidException("Formato de correo invalido, ejemplo usuario@dominio.cl");
        }

        if (!req.getPwd().matches(pass)) {
            throw new ValidException("Formato de clave invalido");
        }

        Optional<Usuarios> a = usuariosRepository.findOneByEmail(req.getEmail());
        
        if (a.isPresent()) {
            throw new EmailException("El correo ya está registrado.");
        }

        log.info("Validacion exitosa");
        return new ResponseEntity<>(this.registroUsuarioService.registroUsuario(req, token), HttpStatus.OK);
        
    }

    @GetMapping(path = "/obtenerUsuario/{idUsuario}", produces = "application/json")
    public ResponseEntity<Usuario> getUsuario(
        @PathVariable(value = "idUsuario", required = true) String idUsuario) throws SQLException {
        
        return new ResponseEntity<>(this.registroUsuarioService.consultaUsuario(idUsuario), HttpStatus.OK);
    }

    @GetMapping(path = "/obtenerUsuario/", produces = "application/json")
    public ResponseEntity<List<Usuario>> getAllUsuario() throws SQLException {
        
        return new ResponseEntity<>(this.registroUsuarioService.consultaTodos(), HttpStatus.OK);
    }
    
}
