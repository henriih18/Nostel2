package Sena.ProyectoNostel.domain.service;


import Sena.ProyectoNostel.config.CodigoRegistroIns;
import Sena.ProyectoNostel.domain.dto.InstructorDTO;
import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import Sena.ProyectoNostel.persistence.mapper.InstructorMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final UsuarioRepository usuarioRepository;
    private final InstructorMapper instructorMapper;
    private final PasswordEncoder passwordEncoder;
    private final CodigoRegistroIns codigoRegistroIns;


    public List<InstructorDTO> obtenerInstructores() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toInstructorDTO)
                .collect(Collectors.toList());
    }

    public Optional<InstructorDTO> obtenerPorIdInstructor(Integer idInstructor) {
        return instructorRepository.findById(idInstructor)
                .map(instructorMapper::toInstructorDTO);
    }


    public Optional<Instructor> obtenerPorIdUsuario(Integer idUsuario) {
        return Optional.ofNullable(instructorRepository.findByUsuarioIdUsuario(idUsuario));
    }

    @Transactional
    public InstructorDTO crearInstructor(InstructorDTO instructorDTO) {
        /*log.info("Intentando crear un instructor desde el servicio: {}", instructorDTO);*/

        if (!codigoRegistroIns.validar(instructorDTO.getCodigoSeguridad())) {
            log.warn("Código de seguridad inválido: {}", instructorDTO.getCodigoSeguridad());
            throw new IllegalArgumentException("Código de registro inválido.");
        }

        // Verificar si el correo ya existe en usuarios
        if (usuarioRepository.existsByCorreo(instructorDTO.getCorreo())) {
            log.error("El correo {} ya está registrado", instructorDTO.getCorreo());
            throw new RuntimeException("El correo ya está registrado");
        }

        try {
            // Crear el usuario en la tabla usuarios
            Usuario usuario = new Usuario();
            usuario.setCorreo(instructorDTO.getCorreo());
            usuario.setContrasena(passwordEncoder.encode(instructorDTO.getContrasena()));
            usuario.setRol("INSTRUCTOR");
            usuario = usuarioRepository.save(usuario);
            log.info("Usuario creado: {}", usuario);

            // Mapear DTO a entidad
            Instructor instructor = instructorMapper.toInstructor(instructorDTO);
            instructor.setUsuario(usuario);

            // Guardar en la tabla instructores
            Instructor savedInstructor = instructorRepository.save(instructor);
            log.info("Instructor guardado exitosamente: {}", savedInstructor);

            return instructorMapper.toInstructorDTO(savedInstructor);
        } catch (Exception e) {
            log.error("Error al crear el instructor: {}", e.getMessage());
            // La anotación @Transactional hará rollback automáticamente
            throw new RuntimeException("Error al registrar el instructor: " + e.getMessage());
        }
    }

    public Optional<InstructorDTO> actualizarInstructor(Integer idInstructor, InstructorDTO instructorDTO) {
        return instructorRepository.findById(idInstructor).map(instructorExistente -> {
            // Actualizar el usuario si se cambia el correo o la contraseña
            usuarioRepository.findByCorreo(instructorExistente.getCorreo()).ifPresent(usuario -> {
                if (instructorDTO.getCorreo() != null && !instructorDTO.getCorreo().equals(instructorExistente.getCorreo())) {
                    usuario.setCorreo(instructorDTO.getCorreo());
                }
                if (instructorDTO.getContrasena() != null && !instructorDTO.getContrasena().isEmpty()) {
                    usuario.setContrasena(passwordEncoder.encode(instructorDTO.getContrasena()));
                }
                usuarioRepository.save(usuario);
            });

            // Mapear otros campos desde el DTO a la entidad
            instructorMapper.updateInstructorFromDto(instructorDTO, instructorExistente);

            // Guardar cambios en la tabla instructores
            Instructor instructorActualizado = instructorRepository.save(instructorExistente);
            return instructorMapper.toInstructorDTO(instructorActualizado);
        });
    }

    public void eliminarInstructor(Integer idInstructor) {
        instructorRepository.findById(idInstructor).ifPresent(instructor -> {
            usuarioRepository.findByCorreo(instructor.getCorreo()).ifPresent(usuarioRepository::delete);
            instructorRepository.deleteById(idInstructor);
        });
    }

    public InstructorDTO toInstructorDTO(Instructor instructor) {
        return instructorMapper.toInstructorDTO(instructor);
    }

    public Map<String, Object> obtenerCodigoYTiempo() {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigo", codigoRegistroIns.getCodigoInstructor());
        respuesta.put("segundosRestantes", codigoRegistroIns.getSegundosRestantes());
        return respuesta;
    }
}
