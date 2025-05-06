package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "planes_mejoramiento")
public class PlanMejoramiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlanMejoramiento;

    //FALTA EL ID-APRENDIZ

    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPlanMejoramiento estado = EstadoPlanMejoramiento.Pendiente;

    //relacion aprendiz
    /*@ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    private Aprendiz aprendiz;*/
    @ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    private Aprendiz aprendiz;
    @Column(name = "id_aprendiz")
    private Integer idAprendiz;

    //relacion instructor
    @ManyToOne
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    @JsonBackReference
    private Instructor instructor;
    @Column(name = "id_instructor")
    private Integer idInstructor;

    /*@Transient
    private String nombreInstructor;*/
    public enum EstadoPlanMejoramiento {
        Pendiente,
        En_Progreso,
        Completado

    }


}
