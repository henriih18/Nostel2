package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.FichaCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository  extends FichaCrudRepository {
    /*@Query("SELECT a FROM Ficha a WHERE a.idFicha = :idFicha")
    Optional<Ficha> obtenerFichaPorId(@Param("idFicha") Integer idFicha);

    @Query(value =
            "SELECT f.id_ficha, f.programa, a.id_aprendiz, a.primer_nombre, a.primer_apellido " +
                    "FROM fichas f " +
                    "LEFT JOIN aprendices a ON f.id_ficha = a.id_ficha " +
                    "WHERE f.id_ficha = :idFicha",
            nativeQuery = true)
    List<Object[]> obtenerFichaPorId(@Param("idFicha") Integer idFicha);
*/

    //@Query("SELECT f FROM Ficha f LEFT JOIN FETCH f.aprendices a WHERE f.idFicha = :idFicha")
    //Optional<Ficha> obtenerFichaPorId(@Param("idFicha") Integer idFicha);
    List<Ficha> findAll();
}

