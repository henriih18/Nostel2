

package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.domain.repository.UsuarioRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Usuario;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AprendizServiceImpl implements AprendizService {

    private final AprendizRepository aprendizRepository;
    private final UsuarioRepository usuarioRepository;
    private final AprendizMapper aprendizMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AprendizDTO> obtenerTodos() {
        return aprendizRepository.findAll().stream()
                .map(aprendizMapper::toAprendizDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz) {
        return aprendizRepository.findById(idAprendiz)
                .map(aprendizMapper::toAprendizDTO);
    }

    /*public AprendizDTO findByDocumento(Integer documentoStr) {
        try {
            Integer documento = Integer.valueOf(documentoStr);
            return aprendizRepository.findByDocumento(documento).orElse(null);
        } catch (NumberFormatException e) {
            log.error("Error al convertir el documento a Integer: {}", documentoStr, e);
            return null;
        }
    }*/

    public Optional<AprendizDTO> findByDocumento (Integer documento) {
        if (documento == null) {
            return Optional.empty();
        }
        return aprendizRepository.findByDocumento(documento)
                .map(this::toAprendizDTO);
    }

    public Optional<Aprendiz> obtenerPorIdUsuario(Integer idUsuario) {
        return aprendizRepository.findByUsuarioIdUsuario(idUsuario);
    }

   @Transactional
   public AprendizDTO crear(AprendizDTO aprendizDTO) {
       //log.info("Intentando crear un instructor desde el servicio: {}", instructorDTO);

       // Verificar si el correo ya existe en usuarios
       if (usuarioRepository.existsByCorreo(aprendizDTO.getCorreo())) {
           log.error("El correo {} ya está registrado", aprendizDTO.getCorreo());
           throw new RuntimeException("El correo ya está registrado");
       }

       try {
           // Crear el usuario en la tabla usuarios
           Usuario usuario = new Usuario();
           usuario.setCorreo(aprendizDTO.getCorreo());
           usuario.setContrasena(passwordEncoder.encode(aprendizDTO.getContrasena()));
           usuario.setRol("APRENDIZ");
           usuario = usuarioRepository.save(usuario);
           log.info("Usuario creado: {}", usuario);

           // Mapear DTO a entidad
           Aprendiz aprendiz = aprendizMapper.toAprendiz(aprendizDTO);
           aprendiz.setUsuario(usuario);

           // Guardar en la tabla instructores
           Aprendiz savedAprendiz = aprendizRepository.save(aprendiz);
           log.info("Aprendiz guardado exitosamente: {}", savedAprendiz);

           return aprendizMapper.toAprendizDTO(savedAprendiz);
       } catch (Exception e) {
           log.error("Error al crear el aprendiz: {}", e.getMessage());
           // La anotación @Transactional hará rollback automáticamente
           throw new RuntimeException("Error al registrar el aprendiz: " + e.getMessage());
       }
   }

    @Override
    public Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO) {
        return aprendizRepository.findById(idAprendiz).map(aprendizExistente -> {
            // Actualizar el usuario si se cambia el correo o la contraseña
            usuarioRepository.findByCorreo(aprendizExistente.getCorreo()).ifPresent(usuario -> {
                if (aprendizDTO.getCorreo() != null && !aprendizDTO.getCorreo().equals(aprendizExistente.getCorreo())) {
                    usuario.setCorreo(aprendizDTO.getCorreo());
                }
                if (aprendizDTO.getContrasena() != null && !aprendizDTO.getContrasena().isEmpty()) {
                    usuario.setContrasena(passwordEncoder.encode(aprendizDTO.getContrasena()));
                }
                usuarioRepository.save(usuario);
            });

            // Mapear otros campos desde el DTO a la entidad
            aprendizMapper.updateAprendizFromDto(aprendizDTO, aprendizExistente);

            // Guardar cambios en la tabla aprendices
            Aprendiz aprendizActualizado = aprendizRepository.save(aprendizExistente);
            return aprendizMapper.toAprendizDTO(aprendizActualizado);
        });
    }

    @Override
    public void eliminar(Integer idAprendiz) {
        aprendizRepository.findById(idAprendiz).ifPresent(aprendiz -> {
            usuarioRepository.findByCorreo(aprendiz.getCorreo()).ifPresent(usuarioRepository::delete);
            aprendizRepository.deleteById(idAprendiz);
        });
    }

    public AprendizDTO toAprendizDTO(Aprendiz aprendiz) {
        return aprendizMapper.toAprendizDTO(aprendiz);
    }
}
