package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "actividades_complementarias")
public class ActividadComplementaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer idActividad;

    //falta el ID-APRENDIZ

    private String actvididad;

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
    private Instructor instructor;

    public ActividadComplementaria() {
    }

    public ActividadComplementaria(Integer idActividad, String actvididad, LocalDate fechaAsignacion,
                                   LocalDate fechaEntrega, EstadoActvidad estado, Aprendiz aprendiz,
                                   Instructor instructor) {
        this.idActividad = idActividad;
        this.actvididad = actvididad;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.aprendiz = aprendiz;
        this.instructor = instructor;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getActvididad() {
        return actvididad;
    }

    public void setActvididad(String actvididad) {
        this.actvididad = actvididad;
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
}
