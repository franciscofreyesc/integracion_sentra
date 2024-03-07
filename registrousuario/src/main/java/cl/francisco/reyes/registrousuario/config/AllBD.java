package cl.francisco.reyes.registrousuario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import cl.francisco.reyes.registrousuario.dto.req.Phones;
import cl.francisco.reyes.registrousuario.dto.req.Usuario;
import cl.francisco.reyes.registrousuario.dto.resp.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AllBD {
    
    @Autowired
    private Environment env;

    public Response insert(Usuario req, long usuarioId, String token) throws SQLException{
        try {
            Response resp = new Response();
            LocalDateTime fechaCreacion = LocalDateTime.now();
            String sqlInsert = null;
            int rows = 0;
            long phonesId = 0;
            
            Connection con = connect();
            
            Statement statement = con.createStatement();

            log.info("Insertando Usuario");
            sqlInsert = "insert into usuarios (userid, name, email, pwd) values ("+ usuarioId + ",'" + req.getName() + "','" + req.getEmail() + "','" + req.getPwd() + "')";
            rows = statement.executeUpdate(sqlInsert);
            
            if (rows > 0) {
                log.info("Usuario Insertado");
            }
            
            log.info("Insertando Telefonos");
            for (Phones phones : req.getPhones()) {
                phonesId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
                sqlInsert = "insert into phones (phonesid, number, citycode, countrycode, userid) values ("
                            + phonesId + ",'" + phones.getNumber() + "','" + phones.getCityCode() + "','" + phones.getCountryCode() + "'," + usuarioId + ")";
                rows = statement.executeUpdate(sqlInsert);

                if (rows > 0) {
                    log.info("Telefono Insertados");
                }
            }
            resp.setId(Long.toString(usuarioId));
            resp.setCreated(fechaCreacion.toString());
            resp.setModified(null);
            resp.setLast_login(fechaCreacion.toString());
            resp.setToken(token);
            resp.setIsActive("true");

            closeConnection(con);
            return resp;
        } catch (SQLException e) {
            log.info("Error en Base de datos: {}", e.getMessage());
        }

        return null;
    }

    public Usuario getData(String idUsuario){
        try {
            Usuario usuario = new Usuario();
            List<Phones> arrPhones = new ArrayList<>();
            Phones phones = null;
            Connection con = connect();
            Statement statement = con.createStatement();

            String sqlQuery = "select u.name, u.email, u.pwd, p.number, p.citycode, p.countrycode from usuarios u, phones p where u.userid = p.userid and u.userid = "+ idUsuario + "";

            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                usuario.setName(rs.getString("name"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPwd(rs.getString("pwd"));
                
                phones = new Phones();
                phones.setNumber(rs.getString("number"));
                phones.setCityCode(rs.getString("citycode"));
                phones.setCountryCode(rs.getString("countrycode"));
                arrPhones.add(phones);
                usuario.setPhones(arrPhones);
            }

            closeConnection(con);
            return usuario;
        } catch (SQLException e) {
            log.info("Error al consultar datos: {}", e.getMessage());
        }

        return null;
    }

    public List<Usuario> getAllData(){
        try {
            List<Usuario> arrUsuarios = new ArrayList<>();
            Usuario usuario = null;
            List<Phones> arrPhones = new ArrayList<>();
            Phones phones = null;
            Connection con = connect();
            Statement statement = con.createStatement();

            String sqlQuery = "select u.name, u.email, u.pwd, p.number, p.citycode, p.countrycode from usuarios u, phones p where u.userid = p.userid";

            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setName(rs.getString("name"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPwd(rs.getString("pwd"));
                
                phones = new Phones();
                phones.setNumber(rs.getString("number"));
                phones.setCityCode(rs.getString("citycode"));
                phones.setCountryCode(rs.getString("countrycode"));
                arrPhones.add(phones);
                usuario.setPhones(arrPhones);
                arrUsuarios.add(usuario);
            }

            closeConnection(con);
            return arrUsuarios;
        } catch (SQLException e) {
            log.info("Error al consultar datos: {}", e.getMessage());
        }

        return null;
    }

    private Connection connect(){
        try {
            String url = env.getProperty("spring.datasource.url");
            String username = env.getProperty("spring.datasource.username");
            String passDb = env.getProperty("spring.datasource.password");
            
            Connection connection = DriverManager.getConnection(url, username, passDb);
            log.info("Conexion a BD");

            return connection;
        } catch (SQLException e) {
            log.info("Error al conectarse a la Base de datos: {}", e.getMessage());
        }

        return null;
    }

    private void closeConnection(Connection con){
        try {
            con.close();
        } catch (SQLException e) {
            log.info("Error al cerrar la conexión a BD");
        }
    }

}
