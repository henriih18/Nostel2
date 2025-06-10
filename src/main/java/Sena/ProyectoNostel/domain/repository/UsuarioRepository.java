package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.UsuarioCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends UsuarioCrudRepository {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    /*Aprendiz save(Aprendiz aprendiz);
    Instructor save(Instructor instructor);*/
}
