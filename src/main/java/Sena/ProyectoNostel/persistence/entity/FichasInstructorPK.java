package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class FichasInstructorPK implements Serializable {
    @Column(name = "id_ficha")
    private Integer idFicha;

    @Column(name = "id_instructor")
    private Integer idInstructor;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FichasInstructorPK that = (FichasInstructorPK) o;
        return Objects.equals(idFicha, that.idFicha) &&
                Objects.equals(idInstructor, that.idInstructor);
    }


    @Override
    public int hashCode() {
        return Objects.hash(idFicha, idInstructor);
    }

}
