package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fichas_instructores")
public class FichasInstructor {
    @EmbeddedId
    private FichasInstructorPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idFicha")
    @JoinColumn(name = "id_ficha", insertable = false, updatable = false)
    private Ficha ficha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInstructor")
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    private Instructor instructor;

}
