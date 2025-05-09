
package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.service.ActividadComplementariaService;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actividadComplementarias")
public class ActividadComplementariaController {

    private final ActividadComplementariaService actividadComplementariaService;
    private final InstructorRepository instructorRepository;

    public ActividadComplementariaController(ActividadComplementariaService actividadComplementariaService, InstructorRepository instructorRepository) {
        this.actividadComplementariaService = actividadComplementariaService;
        this.instructorRepository = instructorRepository;
    }

    @GetMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN', 'APRENDIZ')")
    public ResponseEntity<List<ActividadComplementariaDTO>> obtenerActividadesPorAprendiz(@PathVariable Integer idAprendiz) {
        List<ActividadComplementariaDTO> actividades = actividadComplementariaService.obtenerActividadesPorAprendiz(idAprendiz);
        if (actividades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(actividades, HttpStatus.OK);
    }

    @PostMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<?> agregarActividad(
            @PathVariable Integer idAprendiz,
            @RequestBody ActividadComplementariaDTO actividadComplementariaDTO) {
        try {
            // Obtener el usuario autenticado
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername(); // Suponiendo que el username es el correo

            // Buscar el instructor asociado al usuario autenticado
            Instructor instructor = instructorRepository.findByCorreo(username)
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado para el usuario autenticado"));

            // Establecer los IDs
            actividadComplementariaDTO.setIdAprendiz(idAprendiz);
            actividadComplementariaDTO.setIdInstructor(instructor.getIdInstructor());

            // Validar el DTO
            if (actividadComplementariaDTO.getActaNumber() == null || actividadComplementariaDTO.getNombreComite() == null) {
                return new ResponseEntity<>("Faltan campos requeridos (actaNumber, nombreComite)", HttpStatus.BAD_REQUEST);
            }

            // Invocar el servicio
            ActividadComplementariaDTO creado = actividadComplementariaService.agregarActividad(actividadComplementariaDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la actividad: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idAprendiz}/{idActividad}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ActividadComplementariaDTO> actualizarActividad(
            @PathVariable Integer idActividad,
            @RequestBody ActividadComplementariaDTO actividadComplementariaDTO) {
        Optional<ActividadComplementariaDTO> actualizado = actividadComplementariaService.actualizarActividad(idActividad, actividadComplementariaDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idActividad}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<Void> eliminarActividad(
            @PathVariable Integer idAprendiz,
            @PathVariable Integer idActividad) {
        boolean eliminado = actividadComplementariaService.eliminarActividad(idAprendiz, idActividad);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}