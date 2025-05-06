package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<List<ComentarioDTO>> obtenerComentariosPorAprendiz(
            @PathVariable Integer idAprendiz)
            /*@RequestHeader("Authorization") String token)*/ {
        List<ComentarioDTO> comentarios = comentarioService.obtenerComentariosPorAprendiz(idAprendiz);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping("/{idAprendiz}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ComentarioDTO> agregarComentario(
            @PathVariable Integer idAprendiz,
            @RequestBody ComentarioDTO comentarioDTO)
            /*@RequestHeader("Authorization") String token)*/ {
        /*comentario.setIdAprendiz(idAprendiz);*/
        ComentarioDTO nuevoComentario = comentarioService.guardarComentario(idAprendiz, comentarioDTO);
        return ResponseEntity.ok(nuevoComentario);
    }

    /*@PutMapping("/{idAprendiz}/{idComentario}")
    //@PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")

    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable Integer idAprendiz, @PathVariable Integer idComentario, @RequestBody ComentarioDTO comentarioDTO) {
        Optional<ComentarioDTO> actualizado = comentarioService.actualizarComentario(idAprendiz, idComentario, comentarioDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
    @PutMapping("/{idAprendiz}/{idComentario}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<ComentarioDTO> actualizarComentario(
            @PathVariable Integer idAprendiz,
            @PathVariable Integer idComentario,
            @RequestBody ComentarioDTO comentarioDTO) {
        Optional<ComentarioDTO> actualizado = comentarioService.actualizarComentario(idAprendiz, idComentario, comentarioDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idComentario}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'ADMIN')")
    public ResponseEntity<Void> eliminarComentario(
            @PathVariable Integer idAprendiz,
            @PathVariable Integer idComentario) {
        comentarioService.eliminarComentario(idComentario);
        return ResponseEntity.noContent().build();
    }
}