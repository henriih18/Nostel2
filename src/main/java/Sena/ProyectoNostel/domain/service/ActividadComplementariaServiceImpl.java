package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.repository.ActividadComplementariaRepository;
import Sena.ProyectoNostel.persistence.mapper.ActividadComplementariaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActividadComplementariaServiceImpl  implements ActividadComplementariaService {

    @Autowired
    private ActividadComplementariaRepository actividadComplementariaRepository;

    @Autowired
    private ActividadComplementariaMapper actividadComplementariaMapper;

    public ActividadComplementariaServiceImpl() {
        super();
    }

    @Override
    public ActividadComplementariaDTO agregarActividad(ActividadComplementariaDTO actividadComplementariaDTO) {
        return null;
    }

    @Override
    public Optional<ActividadComplementariaDTO> actualizarActividad(Integer idActividad, ActividadComplementariaDTO actividadComplementariaDTO) {
        return Optional.empty();
    }

    @Override
    public boolean eliminarActividad(Integer idAprendiz, Integer idActividad) {
        return false;
    }
}
