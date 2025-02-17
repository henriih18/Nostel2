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

/* recomendable implementar esto
    HHH000038: Composite-id class does not override equals()
    HHH000039: Composite-id class does not override hashCode()*/

    // Implementación de equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si es el mismo objeto, son iguales
        if (o == null || getClass() != o.getClass()) return false; // Si es de otra clase, no son iguales
        FichasInstructorPK that = (FichasInstructorPK) o;
        return Objects.equals(idFicha, that.idFicha) &&
                Objects.equals(idInstructor, that.idInstructor);
    }

    // Implementación de hashCode
    @Override
    public int hashCode() {
        return Objects.hash(idFicha, idInstructor);
    }

    /*
    public FichasInstructorPK() {
    }

    public FichasInstructorPK(Integer idFicha, Integer idInstructor) {
        this.idFicha = idFicha;
        this.idInstructor = idInstructor;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

     */
}
