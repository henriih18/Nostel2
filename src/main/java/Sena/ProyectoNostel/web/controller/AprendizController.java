package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.domain.service.AprendizService;
import jakarta.annotation.security.PermitAll;
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
@RequestMapping("/aprendices")
public class AprendizController {

    private final AprendizService aprendizService;

    public AprendizController(AprendizService aprendizService) {
        this.aprendizService = aprendizService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<List<AprendizDTO>> obtenerTodos() {
        List<AprendizDTO> aprendices = aprendizService.obtenerTodos();
        return new ResponseEntity<>(aprendices, HttpStatus.OK);
    }

    /*@GetMapping
    public List<AprendizDTO> obtenerTodo() {
        return aprendizService.obtenerTodos();
    }*/

    /*@GetMapping("/idAprendiz")
    public ResponseEntity<AprendizDTO> obtenerPorId(@PathVariable Integer idAprendiz) {
        Optional<AprendizDTO> aprendiz = aprendizService.obtenerPorIdAprendiz(idAprendiz);
        return aprendiz.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/


    @GetMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'APRENDIZ', 'ADMIN')")
    public ResponseEntity<AprendizDTO> obtenerPorIdAprendiz(@PathVariable Integer idAprendiz) {
        Optional<AprendizDTO> aprendiz = aprendizService.obtenerPorIdAprendiz(idAprendiz);
        return aprendiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<AprendizDTO> obtenerPorIdUsuario(@PathVariable("idUsuario") Integer idUsuario) {
        return aprendizService.obtenerPorIdUsuario(idUsuario)
                .map(aprendiz -> aprendizService.toAprendizDTO(aprendiz))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/RegistroAprendiz")
    @PermitAll
    public ResponseEntity<?> RegistroAprendiz(@RequestBody @Valid AprendizDTO aprendizDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            AprendizDTO creado = aprendizService.crear(aprendizDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'APRENDIZ')")
    //@PreAuthorize("hasAnyRole('ADMIN', 'APRENDIZ')")
    public ResponseEntity<?> crear(@RequestBody @Valid AprendizDTO aprendizDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            AprendizDTO creado = aprendizService.crear(aprendizDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('APRENDIZ', 'ADMIN')")
    public ResponseEntity<AprendizDTO> actualizar(@PathVariable Integer idAprendiz, @RequestBody AprendizDTO aprendizDTO) {
        Optional<AprendizDTO> actualizado = aprendizService.actualizar(idAprendiz, aprendizDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idAprendiz) {
        aprendizService.eliminar(idAprendiz);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*@GetMapping("/{idAprendiz}/comentarios")
    public ResponseEntity<AprendizDTO> obtenerAprendizConComentarios(@PathVariable Integer idAprendiz) {
        AprendizDTO aprendizDTO = aprendizService.obtenerAprendizConComentarios(idAprendiz);
        return ResponseEntity.ok(aprendizDTO);
    }*/
}

