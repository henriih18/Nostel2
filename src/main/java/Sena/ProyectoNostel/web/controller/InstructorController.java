package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.domain.service.InstructorService;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/instructores")
public class InstructorController {
    private final InstructorService instructorService;


    public InstructorController(InstructorService instructorService, UsuarioRepository usuarioRepository) {
        this.instructorService = instructorService;
    }

    // Acceso para ADMIN e INSTRUCTOR
    @GetMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<List<InstructorDTO>> obtenerInstructores() {
        List<InstructorDTO> instructores = instructorService.obtenerInstructores();
        return new ResponseEntity<>(instructores, HttpStatus.OK);
    }

    // Acceso para ADMIN e INSTRUCTOR
    @GetMapping("/{idInstructor}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<InstructorDTO> obtenerPorIdInstructor(@PathVariable Integer idInstructor) {
        Optional<InstructorDTO> instructor = instructorService.obtenerPorIdInstructor(idInstructor);
        return instructor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<InstructorDTO> obtenerPorIdUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return instructorService.obtenerPorIdUsuario(idUsuario)
                .map(instructor -> instructorService.toInstructorDTO(instructor))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Solo ADMIN puede crear instructores
    //@PostMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    /*public ResponseEntity<InstructorDTO> crearInstructor(@RequestBody InstructorDTO instructorDTO) {
        InstructorDTO creado = instructorService.crearInstructor(instructorDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }*/
    @PostMapping("/RegistroInstructor")
    /*@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")*/
    public ResponseEntity<?> crearInstructor(@RequestBody @Valid InstructorDTO instructorDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            InstructorDTO creado = instructorService.crearInstructor(instructorDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

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
