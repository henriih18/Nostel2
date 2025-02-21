package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.service.AprendizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aprendices")
public class AprendizController {

    private final AprendizService aprendizService;

    public AprendizController(AprendizService aprendizService) {
        this.aprendizService = aprendizService;
    }

    @GetMapping
    @PreAuthorize("hasRole('INSTRUCTOR', 'APRENDIZ', 'ADMIN')")
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
    @PreAuthorize("hasRole('INSTRUCTOR', 'APRENDIZ', 'ADMIN')")
    public ResponseEntity<AprendizDTO> obtenerPorId(@PathVariable Integer idAprendiz) {
        Optional<AprendizDTO> aprendiz = aprendizService.obtenerPorIdAprendiz(idAprendiz);
        return aprendiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN', 'APRENDIZ')")
    public ResponseEntity<AprendizDTO> crear(@RequestBody AprendizDTO aprendizDTO) {
        AprendizDTO creado = aprendizService.crear(aprendizDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}")
    @PreAuthorize("hasRole('APRENDIZ')")
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

