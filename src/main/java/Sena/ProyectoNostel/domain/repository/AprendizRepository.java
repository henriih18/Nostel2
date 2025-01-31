package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.crud.AprendizCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.mapper.AprendizMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface AprendizRepository extends AprendizCrudRepository {

    // Puedes agregar métodos personalizados aquí si es necesario

    default List<AprendizDTO> obtenerTodosDTO() {
        return findAll().stream()
                .map(AprendizMapper.INSTANCE::toAprendizDTO) // Usa el Mapper
                .collect(Collectors.toList());
    }

    default Optional<AprendizDTO> obtenerPorIdDTO(Integer idAprendiz) {
        return findById(idAprendiz).map(AprendizMapper.INSTANCE::toAprendizDTO);}

    @Query("SELECT  a from Aprendiz a left join fetch a.comentarios c left join fetch c.instructor where a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> obtenerConComentarios(@Param("idAprendiz")Integer idAprendiz);
}

