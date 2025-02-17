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
    @ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    private Aprendiz aprendiz;

    //relacion instructor
    @ManyToOne
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    @JsonBackReference
    private Instructor instructor;
    @Column(name = "id_instructor")
    private Integer idInstructor;

    @Transient
    private String nombreInstructor;

    @PostLoad
    private void postload() {
        if (instructor != null) {
            this.nombreInstructor = instructor.getNombres() + " " + instructor.getApellidos();
        }
    }

    /*
    public PlanMejoramiento() {
    }

    public PlanMejoramiento(Integer idPlanMejoramiento, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, EstadoPlanMejoramiento estado, Aprendiz aprendiz, Instructor instructor, Integer idInstructor, String nombreInstructor) {
        this.idPlanMejoramiento = idPlanMejoramiento;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.aprendiz = aprendiz;
        this.instructor = instructor;
        this.idInstructor = idInstructor;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdPlanMejoramiento() {
        return idPlanMejoramiento;
    }

    public void setIdPlanMejoramiento(Integer idPlanMejoramiento) {
        this.idPlanMejoramiento = idPlanMejoramiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoPlanMejoramiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlanMejoramiento estado) {
        this.estado = estado;
    }

    public Aprendiz getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

     */
}
