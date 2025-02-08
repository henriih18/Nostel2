package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.repository.PlanMejoramientoRepository;
import Sena.ProyectoNostel.persistence.mapper.PlanMejoramientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanMejoramientoServiceImpl implements PlanMejoramientoService {
    @Autowired
    private PlanMejoramientoRepository planMejoramientoRepository;

    @Autowired
    private PlanMejoramientoMapper planMejoramientoMapper;

    public PlanMejoramientoServiceImpl() {
        super();
    }

    @Override
    public PlanMejoramientoDTO agregarPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO) {
        return null;
    }

    @Override
    public Optional<PlanMejoramientoDTO> actualizarPlanMejoramiento(Integer idPlanMejoramiento, PlanMejoramientoDTO planMejoramientoDTO) {
        return Optional.empty();
    }

    @Override
    public boolean eliminarPlanMejoramiento(Integer idAprendiz, Integer idPlanMejoramiento) {
        return false;
    }
}
