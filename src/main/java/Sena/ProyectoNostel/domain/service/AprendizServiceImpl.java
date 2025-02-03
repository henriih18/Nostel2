/*package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AprendizServiceImpl implements AprendizService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Override
    public List<AprendizDTO> obtenerTodos() {
        return aprendizRepository.obtenerTodosDTO();
    }

    @Override
    public Optional<AprendizDTO> obtenerPorIdAprendiz(Integer idAprendiz) {
        return aprendizRepository.obtenerPorIdDTO(idAprendiz);
    }

    @Override
    public AprendizDTO crear(AprendizDTO aprendizDTO) {
        // Aquí puedes agregar lógica de negocio antes de crear el aprendiz
        Aprendiz aprendiz = aprendizRepository.convertirAaprendiz(aprendizDTO);
        aprendiz = aprendizRepository.save(aprendiz);
        return aprendizRepository.convertirAaprendizDTO(aprendiz);
    }

    @Override
    public Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO) {
        return aprendizRepository.findById(idAprendiz).map(aprendizExistente -> {
            aprendizExistente.setPrimerNombre(aprendizDTO.getPrimerNombre());
            aprendizExistente.setSegundoNombre(aprendizDTO.getSegundoNombre());
            aprendizExistente.setPrimerApellido(aprendizDTO.getPrimerApellido());
            aprendizExistente.setSegundoApellido(aprendizDTO.getSegundoApellido());
            aprendizExistente.setFechaNacimiento(aprendizDTO.getFechaNacimiento());
            aprendizExistente.setGenero(aprendizDTO.getGenero());
            aprendizExistente.setCorreo(aprendizDTO.getCorreo());
            aprendizExistente.setTelefono(aprendizDTO.getTelefono());
            aprendizExistente.setResidencia(aprendizDTO.getResidencia());
            /*aprendizExistente.setDiscapacidad(aprendizDTO.getDiscapacidad());
            aprendizExistente.setGrupoEtnico(aprendizDTO.getGrupoEtnico());
            Aprendiz aprendizActualizado = aprendizRepository.save(aprendizExistente);
            return aprendizRepository.convertirAaprendizDTO(aprendizActualizado);
        });
    }

    @Override
    public void eliminar(Integer idAprendiz) {
        // Aquí puedes agregar lógica de negocio antes de eliminar el aprendiz
        aprendizRepository.deleteById(idAprendiz);
    }
}*/


package Sena.ProyectoNostel.domain.service;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.domain.repository.AprendizRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AprendizServiceImpl implements AprendizService {

    @Autowired
    private AprendizRepository aprendizRepository;

    @Autowired
    private AprendizMapper aprendizMapper;

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

    @Override
    public AprendizDTO crear(AprendizDTO aprendizDTO) {
        Aprendiz aprendiz = aprendizMapper.toAprendiz(aprendizDTO);
        aprendiz = aprendizRepository.save(aprendiz);
        return aprendizMapper.toAprendizDTO(aprendiz);
    }

    @Override
    public Optional<AprendizDTO> actualizar(Integer idAprendiz, AprendizDTO aprendizDTO) {
        return aprendizRepository.findById(idAprendiz).map(aprendizExistente -> {
            aprendizMapper.updateAprendizFromDto(aprendizDTO, aprendizExistente);
            Aprendiz aprendizActualizado = aprendizRepository.save(aprendizExistente);
            return aprendizMapper.toAprendizDTO(aprendizActualizado);
        });
    }

    @Override
    public void eliminar(Integer idAprendiz) {
        aprendizRepository.deleteById(idAprendiz);
    }

    /*@Override
    public AprendizDTO obtenerAprendizConComentarios(Integer idAprendiz) {
        Aprendiz aprendiz = aprendizRepository.obtenerConComentarios(idAprendiz)
                .orElseThrow(() -> new RuntimeException("Aprendiz no encontrado"));
        return aprendizMapper.toAprendizDTO(aprendiz);
    }*/




}

