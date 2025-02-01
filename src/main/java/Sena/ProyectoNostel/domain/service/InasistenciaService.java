package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.InasistenciaDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface InasistenciaService {

    InasistenciaDTO agregarInasistencia(InasistenciaDTO inasistenciaDTO);

    Optional<InasistenciaDTO> actualizarInasistencia(Integer idInasistencia, InasistenciaDTO inasistenciaDTO);

    boolean eliminarInasistencia(Integer idAprendiz, Integer idInasistencia);

}
