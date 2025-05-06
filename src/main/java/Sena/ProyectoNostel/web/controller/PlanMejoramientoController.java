package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.service.ActividadComplementariaService;
import Sena.ProyectoNostel.domain.service.PlanMejoramientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planMejoramientos")
public class PlanMejoramientoController {
   /* private final PlanMejoramientoService planMejoramientoService;

    public PlanMejoramientoController(PlanMejoramientoService planMejoramientoService) {
        this.planMejoramientoService = planMejoramientoService;
    }


    @PostMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<PlanMejoramientoDTO> agregarPlanMejoramiento(@RequestBody PlanMejoramientoDTO planMejoramientoDTO ){
        PlanMejoramientoDTO creado = planMejoramientoService.agregarPlanMejoramiento(planMejoramientoDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}/{idPlanMejoramiento}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<PlanMejoramientoDTO> actualizarPlanMejoramiento(@PathVariable Integer idPlanMejoramiento, @RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        Optional<PlanMejoramientoDTO> actualizado = planMejoramientoService.actualizarPlanMejoramiento(idPlanMejoramiento, planMejoramientoDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idPlanMejoramiento}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer idAprendiz, @PathVariable Integer idPlanMejoramiento) {
        boolean eliminado = planMejoramientoService.eliminarPlanMejoramiento(idAprendiz, idPlanMejoramiento);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/

   private final PlanMejoramientoService planMejoramientoService;

    public PlanMejoramientoController(PlanMejoramientoService planMejoramientoService) {
        this.planMejoramientoService = planMejoramientoService;
    }


    @GetMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN', 'APRENDIZ')")

    public ResponseEntity<List<PlanMejoramientoDTO>> obtenerPlanesPorAprendiz(@PathVariable Integer idAprendiz) {
        List<PlanMejoramientoDTO> planes = planMejoramientoService.obtenerPlanesPorAprendiz(idAprendiz);
        if (planes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }


    @PostMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<PlanMejoramientoDTO> agregarPlanMejoramiento(@RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        PlanMejoramientoDTO creado = planMejoramientoService.agregarPlanMejoramiento(planMejoramientoDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }


    @PutMapping("/{idAprendiz}/{idPlanMejoramiento}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<PlanMejoramientoDTO> actualizarPlanMejoramiento(
            @PathVariable Integer idPlanMejoramiento,
            @RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        Optional<PlanMejoramientoDTO> actualizado = planMejoramientoService.actualizarPlanMejoramiento(idPlanMejoramiento, planMejoramientoDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{idAprendiz}/{idPlanMejoramiento}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<Void> eliminarPlanMejoramiento(
            @PathVariable Integer idAprendiz,
            @PathVariable Integer idPlanMejoramiento) {
        boolean eliminado = planMejoramientoService.eliminarPlanMejoramiento(idAprendiz, idPlanMejoramiento);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
