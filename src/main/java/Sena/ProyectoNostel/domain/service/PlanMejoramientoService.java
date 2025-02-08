package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlanMejoramientoService {
    PlanMejoramientoDTO agregarPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO);
    Optional<PlanMejoramientoDTO> actualizarPlanMejoramiento(Integer idPlanMejoramiento, PlanMejoramientoDTO planMejoramientoDTO);
    boolean eliminarPlanMejoramiento(Integer idAprendiz  ,Integer idPlanMejoramiento);
}
