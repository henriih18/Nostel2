package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.repository.ActividadComplementariaRepository;
import Sena.ProyectoNostel.domain.repository.PlanMejoramientoRepository;
import Sena.ProyectoNostel.persistence.mapper.ActividadComplementariaMapper;
import Sena.ProyectoNostel.persistence.mapper.PlanMejoramientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanMejoramientoServiceImpl implements PlanMejoramientoService {
/*
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

    @Override
    public List<PlanMejoramientoDTO> obtenerActividadesPorAprendiz(Integer idAprendiz) {
        return List.of();
    }*/

    @Autowired
    private PlanMejoramientoRepository planMejoramientoRepository;

    @Autowired
    private PlanMejoramientoMapper planMejoramientoMapper;

    public PlanMejoramientoServiceImpl() {
        super();
    }

    @Override
    public List<PlanMejoramientoDTO> obtenerPlanesPorAprendiz(Integer idAprendiz) {
        return planMejoramientoRepository
                .findByIdAprendiz(idAprendiz)
                .stream()
                .map(planMejoramientoMapper::toPlanMejoramientoDTO)
                .toList();
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
