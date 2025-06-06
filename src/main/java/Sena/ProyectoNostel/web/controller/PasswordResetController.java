package Sena.ProyectoNostel.web.controller;


import Sena.ProyectoNostel.domain.service.PasswordResetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

    //private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    @Autowired
    private PasswordResetService passwordResetService;

    public static class PasswordResetRequest {
        private String email;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        //logger.info("Cuerpo recibido: {}", request.getEmail()); // Depuración
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío.");
        }
        passwordResetService.createPasswordResetToken(request.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Se ha enviado un enlace de restablecimiento a tu correo.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        passwordResetService.resetPassword(token, newPassword);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Contraseña actualizada correctamente.");
        return ResponseEntity.ok(response);
    }
}