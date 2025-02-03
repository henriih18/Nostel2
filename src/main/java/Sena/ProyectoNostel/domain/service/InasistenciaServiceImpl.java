package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.InasistenciaDTO;
import Sena.ProyectoNostel.domain.repository.InasistenciaRepository;
import Sena.ProyectoNostel.persistence.mapper.InasistenciaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InasistenciaServiceImpl implements InasistenciaService {
    @Autowired
    private InasistenciaRepository inasistenciaRepository;

    @Autowired
    private InasistenciaMapper inasistenciaMapper;

    public InasistenciaServiceImpl() {
        super();
    }

    @Override
    public InasistenciaDTO agregarInasistencia(InasistenciaDTO inasistenciaDTO) {
        return null;
    }

    @Override
    public Optional<InasistenciaDTO> actualizarInasistencia(Integer idInasistencia, InasistenciaDTO inasistenciaDTO) {
        return Optional.empty();
    }

    @Override
    public boolean eliminarInasistencia(Integer idInasistencia, Integer inasistenciaDTO) {
        return false;
    }
}
