package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.FichaCrudRepository;
import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import Sena.ProyectoNostel.persistence.entity.Ficha;
import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FichaRepository  extends FichaCrudRepository {
    List<Ficha> findAll();
    Optional<Ficha> findByNumeroFicha(Integer numeroFicha);

    @Query("SELECT f FROM Ficha f WHERE DATEDIFF(:currentDate, f.fechaInicio) <= 60")
    List<Ficha> findFichasDisponibles(LocalDate currentDate);


}

