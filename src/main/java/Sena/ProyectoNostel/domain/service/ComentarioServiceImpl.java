package Sena.ProyectoNostel.domain.service;


import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.repository.ComentarioRepository;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import Sena.ProyectoNostel.persistence.entity.Instructor;

import Sena.ProyectoNostel.domain.repository.InstructorRepository;
import Sena.ProyectoNostel.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public ComentarioServiceImpl() {
        super();
    }


    public List<ComentarioDTO> obtenerComentariosPorAprendiz(Integer idAprendiz) {
        return comentarioRepository.findByIdAprendiz(idAprendiz)
                .stream()
                .map(comentario -> {
                    ComentarioDTO dto = new ComentarioDTO();
                    dto.setIdComentario(comentario.getIdComentario());
                    dto.setFechaComentario(comentario.getFechaComentario());
                    dto.setComentario(comentario.getComentario());
                    dto.setIdAprendiz(idAprendiz);
                    dto.setIdInstructor(comentario.getIdInstructor());

                    // Obtener el nombre del instructor
                    Instructor instructor = instructorRepository.findById(comentario.getIdInstructor()).orElse(null);
                    if (instructor != null) {
                        dto.setNombreInstructor(instructor.getNombres() + " " + instructor.getApellidos());
                    } else {
                        dto.setNombreInstructor("Desconocido");
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }

   /* @Override
    public ComentarioDTO agregarComentario(Integer idAprendiz, ComentarioDTO comentarioDTO) {
        return null;
    }*/

    /*public ComentarioDTO guardarComentario(ComentarioDTO comentarioDTO) {
        Integer idComentario = comentarioRepository.AgregarComentario(
                comentarioDTO.getIdAprendiz(),
                comentarioDTO.getFechaComentario(),
                comentarioDTO.getComentario(),
                comentarioDTO.getIdInstructor()
        );
        comentarioDTO.setIdComentario(idComentario);
        return comentarioDTO;
    }*/

    public ComentarioDTO guardarComentario(Integer idAprendiz, ComentarioDTO comentarioDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUsuario = userDetails.getIdUsuario();

        if (idUsuario == null) {
            throw new RuntimeException("No se pudo obtener idUsuario del usuario autenticado");
        }

        // Buscar el instructor asociado al idUsuario
        Instructor instructor = instructorRepository.findByUsuarioIdUsuario(idUsuario);
        if (instructor == null) {
            throw new RuntimeException("Instructor no encontrado para el usuario autenticado");
        }

        // Usar el idInstructor del instructor autenticado
        Integer idComentario = comentarioRepository.AgregarComentario(
                comentarioDTO.getIdAprendiz(),
                comentarioDTO.getFechaComentario(),
                comentarioDTO.getComentario(),
                instructor.getIdInstructor()
        );

        // Configurar el DTO para la respuesta
        comentarioDTO.setIdComentario(idComentario);
        comentarioDTO.setIdInstructor(instructor.getIdInstructor());
        comentarioDTO.setNombreInstructor(instructor.getNombres() + " " + instructor.getApellidos());
        return comentarioDTO;
    }



    /*@Override
    public Optional<ComentarioDTO> actualizarComentario(Integer idAprendiz, Integer idComentario, ComentarioDTO comentarioDTO) {
        return Optional.empty();
    }*/

    @Override
    public Optional<ComentarioDTO> actualizarComentario(Integer idAprendiz, Integer idComentario, ComentarioDTO comentarioDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUsuario = userDetails.getIdUsuario();

        // Buscar el instructor asociado al idUsuario
        Instructor instructor = instructorRepository.findByUsuarioIdUsuario(idUsuario);
        if (instructor == null) {
            throw new RuntimeException("Instructor no encontrado para el usuario: " + idUsuario);
        }

        // Buscar el comentario
        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado: " + idComentario));

        // Validar que el comentario pertenece al aprendiz
        if (!comentario.getIdAprendiz().equals(idAprendiz)) {
            throw new RuntimeException("El comentario no pertenece al aprendiz especificado.");
        }

        // Validar que el instructor autenticado es el autor del comentario
        if (!comentario.getIdInstructor().equals(instructor.getIdInstructor())) {
            throw new RuntimeException("No tienes permiso para actualizar este comentario.");
        }

        // Usar texto plano (sin sanitización)
        String comentarioTexto = comentarioDTO.getComentario();

        // Actualizar los campos del comentario
        comentario.setFechaComentario(comentarioDTO.getFechaComentario());
        comentario.setComentario(comentarioTexto);

        // Guardar los cambios
        comentario = comentarioRepository.save(comentario);

        // Crear el DTO de respuesta
        ComentarioDTO updatedDTO = new ComentarioDTO();
        updatedDTO.setIdComentario(comentario.getIdComentario());
        updatedDTO.setIdAprendiz(comentario.getIdAprendiz());
        updatedDTO.setFechaComentario(comentario.getFechaComentario());
        updatedDTO.setComentario(comentarioTexto);
        updatedDTO.setIdInstructor(instructor.getIdInstructor());
        updatedDTO.setNombreInstructor(instructor.getNombres() + " " + instructor.getApellidos());

        return Optional.of(updatedDTO);
    }


    /*public void eliminarComentario(Integer idComentario) {
        comentarioRepository.deleteById(idComentario);
    }*/

    @Override
    public void eliminarComentario(Integer idComentario) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idUsuario = userDetails.getIdUsuario();

        Instructor instructor = instructorRepository.findByUsuarioIdUsuario(idUsuario);
                //.orElseThrow(() -> new RuntimeException("Instructor no encontrado para el usuario: " + idUsuario));

        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado: " + idComentario));

        if (!comentario.getIdInstructor().equals(instructor.getIdInstructor())) {
            throw new RuntimeException("No tienes permiso para eliminar este comentario.");
        }

        comentarioRepository.deleteById(idComentario);
    }
}