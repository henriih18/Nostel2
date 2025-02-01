package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.crud.ComentarioCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import Sena.ProyectoNostel.persistence.mapper.InstructorMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends ComentarioCrudRepository {
    /*List<Comentario> findByIdAprendiz(Integer idAprendiz);
    List<Comentario> findByIdInstructor(Integer idInstructor);*/

    /*@Query("SELECT c FROM Comentario c LEFT JOIN FETCH c.instructor WHERE c.idComentario = :idComentario")
    Optional<Comentario> findByIdWithInstructor(@Param("idComentario") Integer idComentario);*/



}
