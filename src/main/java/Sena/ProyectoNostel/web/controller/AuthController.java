/*



package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.service.JwtService;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    @PersistenceContext
    private EntityManager entityManager; // Para consultar la tabla usuarios sin entidad

    public AuthController(JwtService jwtService,
                          AprendizRepository aprendizRepository,
                          InstructorRepository instructorRepository) {
        this.jwtService = jwtService;
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        // 1️⃣ Buscar en la tabla aprendices
        Optional<Aprendiz> aprendizOptional = aprendizRepository.findByCorreo(request.getCorreo());
        if (aprendizOptional.isPresent()) {
            Aprendiz aprendiz = aprendizOptional.get();
            if (!aprendiz.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("rol", "ROLE_APRENDIZ");

            String token = jwtService.generateToken(claims, aprendiz.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_APRENDIZ", aprendiz.getCorreo(), aprendiz.getNombres()));
        }

        // 2️⃣ Buscar en la tabla instructores
        Optional<Instructor> instructorOptional = instructorRepository.findByCorreo(request.getCorreo());
        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            if (!instructor.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("rol", "ROLE_INSTRUCTOR");

            String token = jwtService.generateToken(claims, instructor.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_INSTRUCTOR", instructor.getCorreo(), instructor.getNombres()));
        }

        // 3️⃣ Buscar en la tabla usuarios sin entidad (para administradores u otros roles)
        Query query = entityManager.createNativeQuery("SELECT contrasena, rol FROM usuarios WHERE correo = ?");
        query.setParameter(1, request.getCorreo());

        try {
            List<Object[]> result = query.getResultList();
            if (result.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            Object[] userData = result.get(0);
            String contrasena = (String) userData[0];
            String rol = (String) userData[1];

            if (!contrasena.equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("rol", "ROLE_" + rol.toUpperCase()); // Convertimos el rol a mayúsculas

            String token = jwtService.generateToken(claims, request.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_" + rol.toUpperCase(), request.getCorreo(), "Usuario " + rol));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el servidor");
        }
    }
}


*/
package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.service.JwtService;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final JwtService jwtService;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    @PersistenceContext
    private EntityManager entityManager; // Para consultar la tabla usuarios sin entidad

    public AuthController(JwtService jwtService,
                          AprendizRepository aprendizRepository,
                          InstructorRepository instructorRepository) {
        this.jwtService = jwtService;
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        // 1️⃣ Buscar en la tabla aprendices
        Optional<Aprendiz> aprendizOptional = aprendizRepository.findByCorreo(request.getCorreo());
        if (aprendizOptional.isPresent()) {
            Aprendiz aprendiz = aprendizOptional.get();
            if (!aprendiz.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("rol", "ROLE_APRENDIZ");

            String token = jwtService.generateToken(claims, aprendiz.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_APRENDIZ", aprendiz.getCorreo(), aprendiz.getNombres()));
        }

        // 2️⃣ Buscar en la tabla instructores
        Optional<Instructor> instructorOptional = instructorRepository.findByCorreo(request.getCorreo());
        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            if (!instructor.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            claims.put("rol", "ROLE_INSTRUCTOR");

            String token = jwtService.generateToken(claims, instructor.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_INSTRUCTOR", instructor.getCorreo(), instructor.getNombres()));
        }

        // 3️⃣ Buscar en la tabla usuarios sin entidad (para administradores u otros roles)
        Query query = entityManager.createNativeQuery("SELECT contrasena, rol FROM usuarios WHERE correo = ?");
        query.setParameter(1, request.getCorreo());

        try {
            List<Object[]> result = query.getResultList();
            if (result.isEmpty()) {
                return ResponseEntity.status(404).body("Usuario no encontrado");
            }

            Object[] userData = result.get(0);
            String contrasena = (String) userData[0];
            String rol = (String) userData[1];

            if (!contrasena.equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }

            HashMap<String, Object> claims = new HashMap<>();
            String rolUpperCase = rol.toUpperCase();

            // Verificar si es administrador para darle todos los permisos
            if (rolUpperCase.equals("ADMIN") || rolUpperCase.equals("ADMINISTRADOR")) {
                claims.put("rol", "ROLE_ADMIN");
                // Agregar permisos adicionales para garantizar acceso completo
                claims.put("authorities", "ADMIN,INSTRUCTOR,APRENDIZ");
                claims.put("scope", "read,write,delete,update");
                claims.put("isAdmin", true);
                claims.put("fullAccess", true);
            } else {
                claims.put("rol", "ROLE_" + rolUpperCase);
            }

            String token = jwtService.generateToken(claims, request.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, claims.get("rol").toString(), request.getCorreo(), "Usuario " + rol));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el servidor: " + e.getMessage());
        }
    }
}