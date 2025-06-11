package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.AprendizCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AprendizRepository extends AprendizCrudRepository {


    /*@Query("SELECT a FROM Aprendiz a WHERE a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> findById(@Param("idAprendiz") Integer idAprendiz);*/


    Optional<Aprendiz> findByCorreo(String correo);
    /*Optional<Aprendiz> findByDocumento(Integer Documento);*/
    Optional<Aprendiz> findByDocumento(Integer documento);
    //Aprendiz save(Aprendiz aprendiz);

    Optional<Aprendiz> findByUsuarioIdUsuario(Integer idUsuario);
}

