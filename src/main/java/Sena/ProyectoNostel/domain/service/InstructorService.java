package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Service
public interface InstructorService {

    List<InstructorDTO> obtenerInstructores();
    Optional<InstructorDTO> obtenerPorIdInstructor(Integer idInstructor);
    Optional<Instructor> obtenerPorIdUsuario(Integer idUsuario);
    InstructorDTO crearInstructor(InstructorDTO instructorDTO);
    Optional<InstructorDTO> actualizarInstructor(
            Integer idInstructor,
            InstructorDTO instructorDTO
    );
    void eliminarInstructor(Integer idInstructor);

    InstructorDTO toInstructorDTO(Instructor instructor);
    Map<String, Object> obtenerCodigoYTiempo();

}
