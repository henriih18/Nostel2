package Sena.ProyectoNostel.persistence.crud;

import Sena.ProyectoNostel.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioCrudRepository  extends JpaRepository<Comentario, Integer> {

}
