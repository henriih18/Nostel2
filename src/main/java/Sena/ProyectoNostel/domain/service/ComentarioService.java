package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public interface ComentarioService {

    
    ComentarioDTO agregarComentario(Integer idAprendiz, ComentarioDTO comentarioDTO);
    Optional<ComentarioDTO> actualizarComentario(Integer idComentario, Integer comentario, ComentarioDTO comentarioDTO);
    boolean eliminarComentario(Integer idComentario, Integer comentarioDTO);

    //boolean eliminarComentario(Integer idComentario, Integer comentario);
}
