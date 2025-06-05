/*
package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import Sena.ProyectoNostel.domain.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        log.info("Intentando autenticar usuario: {}", request.getCorreo());

        // Buscar el usuario en la tabla usuarios
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        if (usuarioOpt.isEmpty()) {
            log.warn("Usuario no encontrado: {}", request.getCorreo());


            throw new UsernameNotFoundException("Usuario no encontrado: " + request.getCorreo());
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar la contraseña
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            log.warn("Contraseña incorrecta para el usuario: {}", request.getCorreo());
            throw new BadCredentialsException("Credenciales inválidas");

        }

        // Determinar el rol
        String rol = "ROLE_" + usuario.getRol().toUpperCase();
        //log.info("Rol asignado al usuario {}: {}", request.getCorreo(), rol);

        // Generar el token JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol);

        String token = jwtService.generateToken(claims, usuario.getCorreo());
        //log.info("Token generado para el usuario {}: {}", usuario.getCorreo(), token);

        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setRol(rol);
        */
/*response.setIdFicha(idFicha);
        response.setNombre(usuario.getNombre());*//*

        response.setIdUsuario(usuario.getIdUsuario());

        return ResponseEntity.ok(response);
    }
}*/

package Sena.ProyectoNostel.web.controller;

import Sena.ProyectoNostel.domain.dto.JwtResponseDTO;
import Sena.ProyectoNostel.domain.dto.LoginRequestDTO;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import Sena.ProyectoNostel.domain.service.JwtService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    //@PermitAll
    @PostMapping("/login")
    //@PreAuthorize("hasAnyRole('APRENDIZ', 'ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        log.info("Intentando autenticar usuario: {}", request.getCorreo());

        // Buscar el usuario en la tabla usuarios
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        if (usuarioOpt.isEmpty()) {
            log.warn("Usuario no encontrado: {}", request.getCorreo());
            throw new UsernameNotFoundException("Usuario no encontrado: " + request.getCorreo());
        }

        Usuario usuario = usuarioOpt.get();

        // Verificar la contraseña
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            log.warn("Contraseña incorrecta para el usuario: {}", request.getCorreo());
            throw new BadCredentialsException("Credenciales inválidas");
        }

        // Obtener UserDetails para el usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());

        // Determinar el rol
        String rol = usuario.getRol().startsWith("ROLE_") ? usuario.getRol() : "ROLE_" + usuario.getRol().toUpperCase();
        log.info("Rol asignado al usuario {}: {}", request.getCorreo(), rol);

        // Generar el token JWT con idUsuario
        String token = jwtService.generateToken(userDetails, usuario.getIdUsuario(), rol);
        log.info("Token generado para el usuario {}: {}", usuario.getCorreo(), token);

        // Construir la respuesta
        JwtResponseDTO response = new JwtResponseDTO();
        response.setToken(token);
        response.setRol(rol);
        response.setIdUsuario(usuario.getIdUsuario());

        return ResponseEntity.ok(response);
    }
}