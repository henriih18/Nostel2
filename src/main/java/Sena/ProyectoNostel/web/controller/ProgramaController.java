package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ProgramaDTO;
import Sena.ProyectoNostel.domain.service.ProgramaService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    @PermitAll
    //@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<List<ProgramaDTO>> obtenerProgramas() {
        try {
            List<ProgramaDTO> programas = programaService.obtenerProgramas();
            return new ResponseEntity<>(programas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{idPrograma}")
   @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")

    public ResponseEntity<ProgramaDTO> obtenerPorIdPrograma(@PathVariable Integer idPrograma) {
        try {
            Optional<ProgramaDTO> programa = programaService.obtenerPorIdPrograma(idPrograma);
            return programa.map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProgramaDTO> crearPrograma(@RequestBody ProgramaDTO programaDTO) {
        try {
            ProgramaDTO nuevoProgramaDTO = programaService.crearPrograma(programaDTO);
            return new ResponseEntity<>(nuevoProgramaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idPrograma}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProgramaDTO> actualizarPrograma(@PathVariable Integer idPrograma, @RequestBody ProgramaDTO programaDTO) {
        try {
            Optional<ProgramaDTO> programaActualizado = programaService.actualizarPrograma(idPrograma, programaDTO);
            return programaActualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{idPrograma}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> eliminarPrograma(@PathVariable Integer idPrograma) {
        try {
            if (programaService.obtenerPorIdPrograma(idPrograma).isPresent()) {
                programaService.eliminarPrograma(idPrograma);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
