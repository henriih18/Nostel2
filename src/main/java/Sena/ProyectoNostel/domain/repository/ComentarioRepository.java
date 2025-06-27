package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.ComentarioCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Comentario;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends ComentarioCrudRepository {

    List<Comentario> findByIdAprendiz(Integer idAprendiz);

    @Procedure(name = "AgregarComentario")
    Integer AgregarComentario(
            @Param("p_id_aprendiz") Integer idAprendiz,
            @Param("p_fecha") java.time.LocalDate fechaComentario,
            @Param("p_comentario") String comentario,
            @Param("p_id_instructor") Integer idInstructor
    );

    long countByAprendiz_IdAprendiz(Integer idAprendiz);

}
