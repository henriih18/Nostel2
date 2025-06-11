package Sena.ProyectoNostel.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping({"/", ""})
    public ResponseEntity<Void> root() {
        return ResponseEntity.ok().build();
    }
}
