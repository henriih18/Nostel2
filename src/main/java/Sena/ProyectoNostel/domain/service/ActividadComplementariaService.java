package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ActividadComplementariaService {
    ActividadComplementariaDTO agregarActividad(ActividadComplementariaDTO actividadComplementariaDTO);
    Optional<ActividadComplementariaDTO> actualizarActividad(Integer idActividad, ActividadComplementariaDTO actividadComplementariaDTO);
    boolean eliminarActividad(Integer idAprendiz, Integer idActividad);
}
