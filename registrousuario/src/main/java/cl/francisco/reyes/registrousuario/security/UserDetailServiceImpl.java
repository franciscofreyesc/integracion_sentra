package cl.francisco.reyes.registrousuario.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.francisco.reyes.registrousuario.repository.UsuariosRepository;
import cl.francisco.reyes.registrousuario.repository.entity.Usuarios;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuariosRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuario = usuarioRepository.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));
        
        return new UserDetailsImpl(usuario);

    }
    
}
