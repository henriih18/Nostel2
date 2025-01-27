package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AprendizService {
    List<AprendizDTO> obtenerTodos();
    Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz);
    AprendizDTO crear(AprendizDTO aprendizDTO);
    Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO);
    void eliminar(Integer idAprendiz);
}
