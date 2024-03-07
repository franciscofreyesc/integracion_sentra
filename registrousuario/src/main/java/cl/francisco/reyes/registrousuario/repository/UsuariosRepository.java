package cl.francisco.reyes.registrousuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.francisco.reyes.registrousuario.repository.entity.Usuarios;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findOneByEmail(String email);

}
