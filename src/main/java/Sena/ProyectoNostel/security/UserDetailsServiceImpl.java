package Sena.ProyectoNostel.security;

import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        log.debug("Buscando usuario con correo: {}", correo);

        // Buscar el usuario en la tabla usuarios
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado con correo: {}", correo);
                    return new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
                });

        log.info("Usuario encontrado: correo={}, rol={}", usuario.getCorreo(), usuario.getRol());
        return new UserDetailsImpl(usuario);
    }
}