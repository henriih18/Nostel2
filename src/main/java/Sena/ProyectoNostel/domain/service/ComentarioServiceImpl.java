package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.ComentarioDTO;
import Sena.ProyectoNostel.domain.repository.ComentarioRepository;
import Sena.ProyectoNostel.persistence.mapper.ComentarioMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    public ComentarioServiceImpl() {
        super();
    }

    @Override
    public ComentarioDTO agregarComentario(Integer idAprendiz, ComentarioDTO comentarioDTO) {
        return null;
    }

    @Override
    public Optional<ComentarioDTO> actualizarComentario(Integer idComentario, Integer comentario, ComentarioDTO comentarioDTO) {
        return Optional.empty();
    }

    @Override
    public boolean eliminarComentario(Integer idComentario, Integer comentarioDTO) {
        return false;
    }
}