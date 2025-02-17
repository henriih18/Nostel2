package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "actividades_complementarias")
public class ActividadComplementaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer idActividad;

    //falta el ID-APRENDIZ

    private String actividad;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoActvidad estado = EstadoActvidad.Pendiente;


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
    private Integer idIntructor;

    @Transient
    private String nombreInstructor;

    @PostLoad
    private void postload() {
        if (instructor != null) {
            this.nombreInstructor = instructor.getNombres() + " " + instructor.getApellidos();
        }
    }
/*
    public ActividadComplementaria() {
    }

    public ActividadComplementaria(Integer idActividad, String actividad, LocalDate fechaAsignacion, LocalDate fechaEntrega, EstadoActvidad estado, Aprendiz aprendiz, Instructor instructor, Integer idIntructor, String nombreInstructor) {
        this.idActividad = idActividad;
        this.actividad = actividad;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.aprendiz = aprendiz;
        this.instructor = instructor;
        this.idIntructor = idIntructor;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoActvidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActvidad estado) {
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

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdIntructor() {
        return idIntructor;
    }

    public void setIdIntructor(Integer idIntructor) {
        this.idIntructor = idIntructor;
    }

 */
}