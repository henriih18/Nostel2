package Sena.ProyectoNostel.domain.repository;

import Sena.ProyectoNostel.persistence.crud.PlanMejoramientoCrudRepository;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanMejoramientoRepository extends PlanMejoramientoCrudRepository {
    List<PlanMejoramiento> findByIdAprendiz(Integer idAprendiz);
}
