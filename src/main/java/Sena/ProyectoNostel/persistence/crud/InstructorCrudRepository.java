package Sena.ProyectoNostel.persistence.crud;

import Sena.ProyectoNostel.persistence.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorCrudRepository  extends JpaRepository<Instructor, Integer> {
}
