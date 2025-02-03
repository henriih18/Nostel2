package Sena.ProyectoNostel.persistence.crud;

import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadComplementariaCrudRepository extends JpaRepository<ActividadComplementaria, Integer> {
}
