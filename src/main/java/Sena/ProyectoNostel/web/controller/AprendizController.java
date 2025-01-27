package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.service.AprendizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("aprendices")
public class AprendizController {

    @Autowired
    private AprendizService aprendizService;

    public AprendizController(AprendizService aprendizService) {
        this.aprendizService = aprendizService;
    }

    @GetMapping
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
    public ResponseEntity<AprendizDTO> obtenerPorId(@PathVariable Integer idAprendiz) {
        Optional<AprendizDTO> aprendiz = aprendizService.obtenerPorIdAprendiz(idAprendiz);
        return aprendiz.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AprendizDTO> crear(@RequestBody AprendizDTO aprendizDTO) {
        AprendizDTO creado = aprendizService.crear(aprendizDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idAprendiz}")
    public ResponseEntity<AprendizDTO> actualizar(@PathVariable Integer idAprendiz, @RequestBody AprendizDTO aprendizDTO) {
        Optional<AprendizDTO> actualizado = aprendizService.actualizar(idAprendiz, aprendizDTO);
        return actualizado.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idAprendiz}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer idAprendiz) {
        aprendizService.eliminar(idAprendiz);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

