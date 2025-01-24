package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fichas_instructores")
public class FichasInstructor {
    @EmbeddedId
    private FichasInstructorPK id;

    @ManyToOne
    @MapsId("idFicha")
    @JoinColumn(name = "id_ficha", insertable = false, updatable = false)
    private Fichas ficha;

    @ManyToOne
    @MapsId("idInstructor")
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    private Instructor instructor;

    public FichasInstructor() {
    }

    public FichasInstructor(FichasInstructorPK id, Fichas ficha, Instructor instructor) {
        this.id = id;
        this.ficha = ficha;
        this.instructor = instructor;
    }

    public FichasInstructorPK getId() {
        return id;
    }

    public void setId(FichasInstructorPK id) {
        this.id = id;
    }

    public Fichas getFicha() {
        return ficha;
    }

    public void setFicha(Fichas ficha) {
        this.ficha = ficha;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
}
