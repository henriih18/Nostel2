package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.crud.InstructorCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.persistence.mapper.InstructorMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstructorRepository extends InstructorCrudRepository {
    /*@Query("SELECT i FROM Instructor i WHERE i.idInstructor = :idInstructor")
    Optional<Instructor> findById(@Param("idInstructor") Integer idInstructor);

    @Query("SELECT c FROM Comentario c LEFT JOIN FETCH c.instructor WHERE c.idComentario = :idComentario")
    Optional<Comentario> findByIdWithInstructor(@Param("idComentario") Integer idComentario);*/

    /*default Optional<InstructorDTO> obtenerPorIdDTO(Integer idInstructor) {
        return findById(idInstructor).map(InstructorMapper.INSTANCE::toInstructorDTO);
    }*/
}
