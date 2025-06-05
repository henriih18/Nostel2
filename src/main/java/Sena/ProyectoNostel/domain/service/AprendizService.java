package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;

import java.util.List;
import java.util.Optional;

//@Service
public interface AprendizService {
    List<AprendizDTO> obtenerTodos();
    Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz);
    Optional<AprendizDTO> findByDocumento(Integer documento);
    Optional<Aprendiz> obtenerPorIdUsuario(Integer idUsuario);
    AprendizDTO crear(AprendizDTO aprendizDTO);
    Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO);
    void eliminar(Integer idAprendiz);

    //AprendizDTO obtenerAprendizConComentarios(Integer idAprendiz);
    AprendizDTO toAprendizDTO(Aprendiz aprendiz);


}
