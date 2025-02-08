package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.service.ComentarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/{idAprendiz}")
    public ResponseEntity<ComentarioDTO> agregarComentario(@PathVariable Integer idAprendiz, @RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO creado = comentarioService.agregarComentario(idAprendiz, comentarioDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}/{idComentario}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable Integer idAprendiz, @PathVariable Integer idComentario, @RequestBody ComentarioDTO comentarioDTO) {
        Optional<ComentarioDTO> actualizado = comentarioService.actualizarComentario(idAprendiz, idComentario, comentarioDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}/{idComentario}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Integer idAprendiz, @PathVariable Integer idComentario) {
        boolean eliminado = comentarioService.eliminarComentario(idAprendiz, idComentario);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}