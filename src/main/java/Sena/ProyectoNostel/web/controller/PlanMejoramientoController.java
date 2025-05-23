package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.service.ActividadComplementariaService;
import Sena.ProyectoNostel.domain.service.PlanMejoramientoService;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/planMejoramientos")
public class PlanMejoramientoController {

    private final PlanMejoramientoService planMejoramientoService;
    private final InstructorRepository instructorRepository;

    public PlanMejoramientoController(PlanMejoramientoService planMejoramientoService,
                                             InstructorRepository instructorRepository) {
        this.planMejoramientoService = planMejoramientoService ;
        this.instructorRepository = instructorRepository;
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
    public ResponseEntity<?> agregarPlanMejoramiento(
            @PathVariable Integer idAprendiz,
            @RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        try {
            // Obtener el usuario autenticado
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername(); // Suponiendo que el username es el correo

            // Buscar el instructor asociado al usuario autenticado
            Instructor instructor = instructorRepository.findByCorreo(username)
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado para el usuario autenticado"));

            // Establecer los IDs
            planMejoramientoDTO.setIdAprendiz(idAprendiz);
            planMejoramientoDTO.setIdInstructor(instructor.getIdInstructor());

            // Validar el DTO
            if (planMejoramientoDTO.getActaNumber() == null || planMejoramientoDTO.getActaNumber().trim().isEmpty()) {
                return new ResponseEntity<>("El número de acta es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getNombreComite() == null || planMejoramientoDTO.getNombreComite().trim().isEmpty()) {
                return new ResponseEntity<>("El nombre del comité es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getFecha() == null) {
                return new ResponseEntity<>("La fecha del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getObjetivos() == null || planMejoramientoDTO.getObjetivos().trim().isEmpty()) {
                return new ResponseEntity<>("Los objetivos del acta son obligatorios y no pueden estar vacíos.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getCiudad() == null || planMejoramientoDTO.getCiudad().trim().isEmpty()) {
                return new ResponseEntity<>("La ciudad del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getHoraInicio() == null || planMejoramientoDTO.getHoraInicio().trim().isEmpty()) {
                return new ResponseEntity<>("La hora de inicio del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getHoraFin() == null || planMejoramientoDTO.getHoraFin().trim().isEmpty()) {
                return new ResponseEntity<>("La hora de fin del acta es obligatoria.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getLugarEnlace() == null || planMejoramientoDTO.getLugarEnlace().trim().isEmpty()) {
                return new ResponseEntity<>("El lugar o enlace del acta es obligatorio.", HttpStatus.BAD_REQUEST);
            }
            if (planMejoramientoDTO.getAsistentesPlan() == null || planMejoramientoDTO.getAsistentesPlan().isEmpty()) {
                return new ResponseEntity<>("El acta debe incluir al menos un asistente.", HttpStatus.BAD_REQUEST);
            }
            for (var asistente : planMejoramientoDTO.getAsistentesPlan()) {
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

            // Invocar el servicio
            PlanMejoramientoDTO creado = planMejoramientoService.agregarPlanMejoramiento(planMejoramientoDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la actividad: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idAprendiz}/{idPlanMejoramiento}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<PlanMejoramientoDTO> actualizarPlanMejoramiento(
            @PathVariable Integer idPlanMejoramiento,
            @RequestBody PlanMejoramientoDTO planMejoramientoDTO) {
        Optional<PlanMejoramientoDTO> actualizado =
                planMejoramientoService.actualizarPlanMejoramiento(idPlanMejoramiento,
                planMejoramientoDTO);
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
