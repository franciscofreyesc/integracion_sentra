package cl.francisco.reyes.registrousuario.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Usuarios {
    @Id
    @Column(name = "userid")
    private Long id;

    @Column(name = "name")
    String name;
    
    @Column(name = "email")
    String email;
    
    @Column(name = "pwd")
    String pwd;
}
