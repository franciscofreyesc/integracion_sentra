package cl.francisco.reyes.registrousuario.dto.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {
    String name;
    String email;
    @JsonProperty("password")
    String pwd;
    List<Phones> phones;
}
