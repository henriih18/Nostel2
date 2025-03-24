package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.FichaDTO;
import Sena.ProyectoNostel.domain.service.FichaService;
import Sena.ProyectoNostel.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/fichas")

public class FichaController {
    private final FichaService fichaService;

    public FichaController(FichaService fichaService) {
        this.fichaService = fichaService;
    }

    @GetMapping
    //@JsonView(Views.FichaView.class)
    public ResponseEntity<List<FichaDTO>> obtenerFichas() {
        List<FichaDTO> fichas = fichaService.obtenerFichas();
        return new ResponseEntity<>(fichas, HttpStatus.OK);
    }

    @GetMapping("/{idFicha}")
    @JsonView(Views.FichaView.class)

    public ResponseEntity<FichaDTO> obtenerFichaPorId(@PathVariable Integer idFicha) {
        Optional<FichaDTO> ficha = fichaService.obtenerFichaId(idFicha);
        return ficha.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FichaDTO> crearFicha(@RequestBody FichaDTO fichaDTO) {
        FichaDTO creado = fichaService.crearFicha(fichaDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{idFicha}")
    public ResponseEntity<FichaDTO> actualizarFicha (@PathVariable Integer idFicha, @RequestBody FichaDTO fichaDTO) {
        Optional<FichaDTO> actualizado = fichaService.actualizarFicha(idFicha, fichaDTO);
        return actualizado.map( value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{idFicha}")
    public ResponseEntity<String> eliminarFicha(@PathVariable Integer idFicha) {
        fichaService.eliminarFicha(idFicha);
        return new ResponseEntity<>("Ficha eliminada", HttpStatus.OK);
    }


}
