package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.ProgramaDTO;

import java.util.List;
import java.util.Optional;

public interface ProgramaService {
    List<ProgramaDTO> obtenerProgramas();
    Optional<ProgramaDTO> obtenerPorIdPrograma(Integer idPrograma);
    ProgramaDTO crearPrograma(ProgramaDTO programaDTO);
    Optional<ProgramaDTO> actualizarPrograma(Integer idPrograma, ProgramaDTO programaDTO);
    void eliminarPrograma(Integer idPrograma);
}
