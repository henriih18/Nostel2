package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.InasistenciaDTO;
import Sena.ProyectoNostel.domain.service.ActividadComplementariaService;
import Sena.ProyectoNostel.domain.service.InasistenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/actividadComplementarias")

public class ActividadComplementariaController {
    private final ActividadComplementariaService actividadComplementariaService;

    public ActividadComplementariaController(ActividadComplementariaService actividadComplementariaService) {
        this.actividadComplementariaService = actividadComplementariaService;
    }

    @PostMapping("/{idAprendiz}")
    public ResponseEntity<ActividadComplementariaDTO> agregarActividad(@RequestBody ActividadComplementariaDTO actividadComplementariaDTO) {
        ActividadComplementariaDTO creado = actividadComplementariaService.agregarActividad(actividadComplementariaDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}/{idActividad}")
    public ResponseEntity<ActividadComplementariaDTO> actualizarActividad(@PathVariable Integer idActividad, @RequestBody ActividadComplementariaDTO actividadComplementariaDTO) {
        Optional<ActividadComplementariaDTO> actualizado = actividadComplementariaService.actualizarActividad(idActividad, actividadComplementariaDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idActividad}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Integer idAprendiz, @PathVariable Integer idActividad) {
        boolean eliminado = actividadComplementariaService.eliminarActividad(idAprendiz, idActividad);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
