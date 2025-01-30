package Sena.ProyectoNostel.persistence.crud;

import Sena.ProyectoNostel.persistence.entity.Aprendiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AprendizCrudRepository extends JpaRepository<Aprendiz, Integer> {

}


