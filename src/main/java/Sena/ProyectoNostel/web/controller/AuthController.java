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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
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

        /*// Buscar usuario
        Aprendiz aprendiz = aprendizRepository.findByCorreo(request.getCorreo());
        if (aprendiz != null) {
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
        }*/

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
    }
}
