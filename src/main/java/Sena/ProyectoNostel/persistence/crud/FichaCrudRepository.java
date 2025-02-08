package Sena.ProyectoNostel.persistence.crud;

import Sena.ProyectoNostel.persistence.entity.Ficha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaCrudRepository  extends JpaRepository<Ficha, Integer> {
}
