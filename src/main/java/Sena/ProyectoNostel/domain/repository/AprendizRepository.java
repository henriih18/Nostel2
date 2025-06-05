package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.domain.dto.AprendizDTO;
import Sena.ProyectoNostel.persistence.crud.AprendizCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AprendizRepository extends AprendizCrudRepository {

    // Puedes agregar métodos personalizados aquí si es necesario



    @Query("SELECT a FROM Aprendiz a WHERE a.idAprendiz = :idAprendiz")
    Optional<Aprendiz> findById(@Param("idAprendiz") Integer idAprendiz);


    Optional<Aprendiz> findByCorreo(String correo);
    Optional<Aprendiz> findByDocumento(Integer Documento);
    Aprendiz save(Aprendiz aprendiz);

    Optional<Aprendiz> findByUsuarioIdUsuario(Integer idUsuario);
}

