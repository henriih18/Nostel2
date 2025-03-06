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



   /* @Query("SELECT  a from Aprendiz a left join fetch a.comentarios c   left join fetch c.instructor b left  join fetch b.inasistencias   where a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> obtenerConComentarios(@Param("idAprendiz")Integer idAprendiz);*/

   /* @Query("SELECT DISTINCT a FROM Aprendiz a " +
            "LEFT JOIN FETCH a.comentarios c " +
            "LEFT JOIN FETCH c.instructor b " +
            "LEFT JOIN FETCH b.inasistencias " +
            "WHERE a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> obtenerConComentarios(@Param("idAprendiz") Integer idAprendiz);*/

    @Query("SELECT a FROM Aprendiz a WHERE a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> findById(@Param("idAprendiz") Integer idAprendiz);

    //Optional<Object> findByCorreo(void attr0);

    //Optional<Aprendiz>findByCorreo(String correo);
    //obtenerPorIdAprendiz ensayar

    Optional<Aprendiz> findByCorreo(String correo);
    Aprendiz save(Aprendiz aprendiz);
}

