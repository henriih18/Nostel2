package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.service.PlanMejoramientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("planMejoramientos")
public class PlanMejoramientoController {
    private final PlanMejoramientoService planMejoramientoService;

    public PlanMejoramientoController(PlanMejoramientoService planMejoramientoService) {
        this.planMejoramientoService = planMejoramientoService;
    }


    @PostMapping("/{idAprendiz}")
    public ResponseEntity<PlanMejoramientoDTO> agregarPlanMejoramiento(@RequestBody PlanMejoramientoDTO planMejoramientoDTO ){
        PlanMejoramientoDTO creado = planMejoramientoService.agregarPlanMejoramiento(planMejoramientoDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}/{idPlanMejoramiento}")
    public ResponseEntity<PlanMejoramientoDTO> actualizarPlanMejoramiento(@PathVariable Integer idPlanMejoramiento, @RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        Optional<PlanMejoramientoDTO> actualizado = planMejoramientoService.actualizarPlanMejoramiento(idPlanMejoramiento, planMejoramientoDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idPlanMejoramiento}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer idAprendiz, @PathVariable Integer idPlanMejoramiento) {
        boolean eliminado = planMejoramientoService.eliminarPlanMejoramiento(idAprendiz, idPlanMejoramiento);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
