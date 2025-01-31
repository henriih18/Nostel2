package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.crud.ComentarioCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import Sena.ProyectoNostel.persistence.mapper.InstructorMapper;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends ComentarioCrudRepository {
    List<Comentario> findByIdAprendiz(Integer idAprendiz);

    default Optional<InstructorDTO> obtenerPorIdDTO(Integer idInstructor) {
        return findById(idInstructor).map(InstructorMapper.INSTANCE::toInstructorDTO);
    }
}
