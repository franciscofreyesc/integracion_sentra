package cl.francisco.reyes.registrousuario.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.francisco.reyes.registrousuario.config.AllBD;
import cl.francisco.reyes.registrousuario.dto.req.Usuario;
import cl.francisco.reyes.registrousuario.dto.resp.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistroUsuarioService {

    @Autowired
    AllBD allBd;

    public Response registroUsuario(Usuario req, String token) throws SQLException{

        log.info("[Registrando Usuario] - INICIO");
        
        Response resp = new Response();
        long usuarioId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        resp = allBd.insert(req, usuarioId, token);
        
        log.info("[Registrando Usuario] - FIN");
        return resp;
    }

    public Usuario consultaUsuario(String idUsuario) throws SQLException{

        log.info("[Consultando Usuario {}] - INICIO", idUsuario);
        
        Usuario usuario = new Usuario();
        usuario = allBd.getData(idUsuario);

        log.info("[Consultando Usuario] - FIN");
        return usuario;
    }

    public List<Usuario> consultaTodos() throws SQLException{

        log.info("[Consultando todos los Usuarios] - INICIO");
        
        List<Usuario> usuario = new ArrayList<>();
        usuario = allBd.getAllData();

        log.info("[Consultando todos los Usuarios]");
        return usuario;
    }
}
