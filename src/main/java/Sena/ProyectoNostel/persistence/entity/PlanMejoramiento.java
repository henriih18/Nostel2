package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

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
    private String fechaInicio;

    @Column(name = "fecha_fin")
    private String fechaFin;

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
    private Instructor instructor;

    public PlanMejoramiento() {
    }

    public PlanMejoramiento(Integer idPlanMejoramiento, String descripcion, String fechaInicio,
                            String fechaFin, EstadoPlanMejoramiento estado, Aprendiz aprendiz,
                            Instructor instructor) {
        this.idPlanMejoramiento = idPlanMejoramiento;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.aprendiz = aprendiz;
        this.instructor = instructor;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
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
}
