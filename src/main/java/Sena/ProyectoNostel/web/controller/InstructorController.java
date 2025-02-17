package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.domain.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instructores")
public class InstructorController {
    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    // Acceso para ADMIN e INSTRUCTOR
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<List<InstructorDTO>> obtenerInstructores() {
        List<InstructorDTO> instructores = instructorService.obtenerInstructores();
        return new ResponseEntity<>(instructores, HttpStatus.OK);
    }

    // Acceso para ADMIN e INSTRUCTOR
    @GetMapping("/{idInstructor}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<InstructorDTO> obtenerPorIdInstructor(@PathVariable Integer idInstructor) {
        Optional<InstructorDTO> instructor = instructorService.obtenerPorIdInstructor(idInstructor);
        return instructor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo ADMIN puede crear instructores
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstructorDTO> crearInstructor(@RequestBody InstructorDTO instructorDTO) {
        InstructorDTO creado = instructorService.crearInstructor(instructorDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // INSTRUCTOR puede actualizar su propio perfil, ADMIN puede actualizar cualquier perfil
    @PutMapping("/{idInstructor}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<InstructorDTO> actualizarInstructor(
            @PathVariable Integer idInstructor,
            @RequestBody InstructorDTO instructorDTO) {
        Optional<InstructorDTO> actualizado = instructorService.actualizarInstructor(idInstructor, instructorDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Solo ADMIN puede eliminar instructores
    @DeleteMapping("/{idInstructor}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarInstructor(@PathVariable Integer idInstructor) {
        instructorService.eliminarInstructor(idInstructor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
