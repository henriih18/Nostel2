package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;

import java.util.List;
import java.util.Optional;

//@Service
public interface ComentarioService {

    
    //ComentarioDTO agregarComentario(Integer idAprendiz, ComentarioDTO comentarioDTO);
    Optional<ComentarioDTO> actualizarComentario(Integer idAprendiz, Integer idComentario, ComentarioDTO comentarioDTO);
    void eliminarComentario(Integer idComentario);

    //boolean eliminarComentario(Integer idComentario, Integer comentario);
    List<ComentarioDTO> obtenerComentariosPorAprendiz(Integer idAprendiz);

    ComentarioDTO guardarComentario(Integer idAprendiz, ComentarioDTO comentario);
}
