
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
            if (actividadComplementariaDTO.getActaNumber() == null || actividadComplementariaDTO.getActaNumber().trim().isEmpty()) {
                return new ResponseEntity<>("El número de acta es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getNombreComite() == null || actividadComplementariaDTO.getNombreComite().trim().isEmpty()) {
                return new ResponseEntity<>("El nombre del comité es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getFecha() == null) {
                return new ResponseEntity<>("La fecha del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getObjetivos() == null || actividadComplementariaDTO.getObjetivos().trim().isEmpty()) {
                return new ResponseEntity<>("Los objetivos del acta son obligatorios y no pueden estar vacíos.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getCiudad() == null || actividadComplementariaDTO.getCiudad().trim().isEmpty()) {
                return new ResponseEntity<>("La ciudad del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getHoraInicio() == null || actividadComplementariaDTO.getHoraInicio().trim().isEmpty()) {
                return new ResponseEntity<>("La hora de inicio del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getHoraFin() == null || actividadComplementariaDTO.getHoraFin().trim().isEmpty()) {
                return new ResponseEntity<>("La hora de fin del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getLugarEnlace() == null || actividadComplementariaDTO.getLugarEnlace().trim().isEmpty()) {
                return new ResponseEntity<>("El lugar o enlace del acta es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (actividadComplementariaDTO.getAsistentes() == null || actividadComplementariaDTO.getAsistentes().isEmpty()) {
                return new ResponseEntity<>("El acta debe incluir al menos un asistente.", HttpStatus.BAD_REQUEST);
            }
            for (var asistente : actividadComplementariaDTO.getAsistentes()) {
                if (asistente.getNombre() == null || asistente.getNombre().trim().isEmpty()) {
                    return new ResponseEntity<>("El nombre del asistente es obligatorio.", HttpStatus.BAD_REQUEST);
                }
                if (asistente.getNumeroDocumento() == null || asistente.getNumeroDocumento().trim().isEmpty()) {
                    return new ResponseEntity<>("El número de documento del asistente es obligatorio.", HttpStatus.BAD_REQUEST);
                }
                if (asistente.getFirmaParticipacion() == null || asistente.getFirmaParticipacion().trim().isEmpty()) {
                    return new ResponseEntity<>("La firma o participación virtual del asistente es obligatoria.", HttpStatus.BAD_REQUEST);
                }
            }
            for (var asistente : actividadComplementariaDTO.getAsistentes()) {
                System.out.println("---- ASISTENTE ----");
                System.out.println("Nombre: " + asistente.getNombre());
                System.out.println("Planta: " + asistente.getPlanta());
                System.out.println("Contratista: " + asistente.getContratista());
                System.out.println("Autoriza Grabación: " + asistente.getAutorizaGrabacion());
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