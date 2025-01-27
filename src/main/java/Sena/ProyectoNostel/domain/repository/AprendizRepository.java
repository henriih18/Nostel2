package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.crud.AprendizCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface AprendizRepository extends AprendizCrudRepository {

    // Puedes agregar métodos personalizados aquí si es necesario

    default List<AprendizDTO> obtenerTodosDTO() {
        return findAll().stream()
                .map(this::convertirAaprendizDTO)
                .collect(Collectors.toList());
    }

    default Optional<AprendizDTO> obtenerPorIdDTO(Integer id) {
        return findById(id).map(this::convertirAaprendizDTO);
    }

    default AprendizDTO convertirAaprendizDTO(Aprendiz aprendiz) {
        AprendizDTO aprendizDTO = new AprendizDTO();
        aprendizDTO.setIdAprendiz(aprendiz.getIdAprendiz());
        aprendizDTO.setPrimerNombre(aprendiz.getPrimerNombre());
        aprendizDTO.setSegundoNombre(aprendiz.getSegundoNombre());
        aprendizDTO.setPrimerApellido(aprendiz.getPrimerApellido());
        aprendizDTO.setSegundoApellido(aprendiz.getSegundoApellido());
        aprendizDTO.setFechaNacimiento(aprendiz.getFechaNacimiento());
        aprendizDTO.setGenero(aprendiz.getGenero());
        aprendizDTO.setCorreo(aprendiz.getCorreo());
        aprendizDTO.setTelefono(aprendiz.getTelefono());
        aprendizDTO.setResidencia(aprendiz.getResidencia());
        /*aprendizDTO.setDiscapacidad(aprendiz.getDiscapacidad());*/
        aprendizDTO.setGrupoEtnico(aprendiz.getGrupoEtnico());
        return aprendizDTO;
    }

    default Aprendiz convertirAaprendiz(AprendizDTO aprendizDTO) {
        Aprendiz aprendiz = new Aprendiz();
        aprendiz.setIdAprendiz(aprendizDTO.getIdAprendiz());
        aprendiz.setPrimerNombre(aprendizDTO.getPrimerNombre());
        aprendiz.setSegundoNombre(aprendizDTO.getSegundoNombre());
        aprendiz.setPrimerApellido(aprendizDTO.getPrimerApellido());
        aprendiz.setSegundoApellido(aprendizDTO.getSegundoApellido());
        aprendiz.setFechaNacimiento(aprendizDTO.getFechaNacimiento());
        aprendiz.setGenero(aprendizDTO.getGenero());
        aprendiz.setCorreo(aprendizDTO.getCorreo());
        aprendiz.setTelefono(aprendizDTO.getTelefono());
        aprendiz.setResidencia(aprendizDTO.getResidencia());
        /*aprendiz.setDiscapacidad(aprendizDTO.getDiscapacidad());*/
        aprendiz.setGrupoEtnico(aprendizDTO.getGrupoEtnico());
        return aprendiz;
    }
}

