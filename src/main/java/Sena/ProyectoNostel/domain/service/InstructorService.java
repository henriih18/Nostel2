package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InstructorService {

    List<InstructorDTO> obtenerInstructores();
    Optional<InstructorDTO> obtenerPorIdInstructor(Integer idInstructor);
    InstructorDTO crearInstructor(InstructorDTO instructorDTO);
    Optional<InstructorDTO> actualizarInstructor(
            Integer idInstructor,
            InstructorDTO instructorDTO
    );
    void eliminarInstructor(Integer idInstructor);
}
