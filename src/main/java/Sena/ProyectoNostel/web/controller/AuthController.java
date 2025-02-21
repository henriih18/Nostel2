/*
package Sena.ProyectoNostel.web.controller;*/
/*
package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.service.JwtService;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasena()
                )
        );

        Optional<Aprendiz> aprendizOptional = aprendizRepository.findByCorreo(request.getCorreo());
        if (aprendizOptional.isPresent()) {
            Aprendiz aprendiz = aprendizOptional.get();
            String token = jwtService.generateToken(
                    new UserDetailsImpl(aprendiz),
                    aprendiz.getPrimerNombre() + " " + aprendiz.getPrimerApellido()
            );
            return ResponseEntity.ok(new JwtResponseDTO(
                    token,
                    "ROLE_APRENDIZ",
                    aprendiz.getCorreo(),
                    aprendiz.getPrimerNombre() + " " + aprendiz.getPrimerApellido()
            ));
        }


        Instructor instructor = instructorRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(
                new UserDetailsImpl(instructor),
                instructor.getNombres() + " " + instructor.getApellidos()
        );

        return ResponseEntity.ok(new JwtResponseDTO(
                token,
                "ROLE_INSTRUCTOR",
                instructor.getCorreo(),
                instructor.getNombres() + " " + instructor.getApellidos()
        ));
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
*//*


import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.service.JwtService;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            // Intentar autenticar las credenciales
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getCorreo(),
                            request.getContrasena()
                    )
            );

            // Buscar si el usuario es un aprendiz
            Optional<Aprendiz> aprendizOptional = aprendizRepository.findByCorreo(request.getCorreo());
            if (aprendizOptional.isPresent()) {
                Aprendiz aprendiz = aprendizOptional.get();
                String token = jwtService.generateToken(
                        new UserDetailsImpl(aprendiz),
                        aprendiz.getPrimerNombre() + " " + aprendiz.getPrimerApellido()
                );
                return ResponseEntity.ok(new JwtResponseDTO(
                        token,
                        "ROLE_APRENDIZ",
                        aprendiz.getCorreo(),
                        aprendiz.getPrimerNombre() + " " + aprendiz.getPrimerApellido()
                ));
            }

            // Si no es un aprendiz, buscar si el usuario es un instructor o admin
            Instructor instructor = instructorRepository.findByCorreo(request.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = jwtService.generateToken(
                    new UserDetailsImpl(instructor),
                    instructor.getNombres() + " " + instructor.getApellidos()
            );

            return ResponseEntity.ok(new JwtResponseDTO(
                    token,
                    "ROLE_INSTRUCTOR",
                    instructor.getCorreo(),
                    instructor.getNombres() + " " + instructor.getApellidos()
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;

    public AuthController(JwtService jwtService, AprendizRepository aprendizRepository, InstructorRepository instructorRepository) {
        this.jwtService = jwtService;
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        Optional<Aprendiz> aprendizOptional = aprendizRepository.findByCorreo(request.getCorreo());
        if (aprendizOptional.isPresent()) {
            Aprendiz aprendiz = aprendizOptional.get();
            if (!aprendiz.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("role", "ROLE_APRENDIZ");

            String token = jwtService.generateToken(claims, aprendiz.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_APRENDIZ", aprendiz.getCorreo(), aprendiz.getPrimerNombre()));
        }

        Optional<Instructor> instructorOptional = instructorRepository.findByCorreo(request.getCorreo());
        if (instructorOptional.isPresent()) {
            Instructor instructor = instructorOptional.get();
            if (!instructor.getContrasena().equals(request.getContrasena())) {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("role", "ROLE_INSTRUCTOR");

            String token = jwtService.generateToken(claims, instructor.getCorreo());
            return ResponseEntity.ok(new JwtResponseDTO(token, "ROLE_INSTRUCTOR", instructor.getCorreo(), instructor.getNombres()));
        }

        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
}

