package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.InasistenciaDTO;
import Sena.ProyectoNostel.domain.service.InasistenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/inasitencias")
public class InasistenciaController {

    private final InasistenciaService inasistenciaService;

    public InasistenciaController(InasistenciaService inasistenciaService) {
        this.inasistenciaService = inasistenciaService;
    }

    @PostMapping("/{idAprendiz}")
    public ResponseEntity<InasistenciaDTO> agregarInasistencia( @RequestBody InasistenciaDTO inasistenciaDTO) {
        InasistenciaDTO creado = inasistenciaService.agregarInasistencia(inasistenciaDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}/{idInasistencia}")
    public ResponseEntity<InasistenciaDTO> actualizarInasistencia(@PathVariable Integer idInasistencia, @RequestBody InasistenciaDTO inasistenciaDTO) {
        Optional<InasistenciaDTO> actualizado = inasistenciaService.actualizarInasistencia(idInasistencia, inasistenciaDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idInasistencia}")
    public ResponseEntity<Void> eliminarInasistencia(@PathVariable Integer idAprendiz, @PathVariable Integer idInasistencia) {
        boolean eliminado = inasistenciaService.eliminarInasistencia(idAprendiz, idInasistencia);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}