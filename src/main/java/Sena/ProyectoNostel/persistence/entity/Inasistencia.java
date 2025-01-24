package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "inasistencias")
public class Inasistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inasistencia")
    private Integer idInasistencia;

    //FALTA EL ID-APRENDIZ

    @Column(name = "fecha")
    private LocalDate fechaInasistencia;

    private String motivo;

    //FALTA ID-INSTRUCTOR

    //relacion aprendiz
    @ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    private Aprendiz aprendiz;

    //relacion instructor
    @ManyToOne
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    private Instructor instructor;

    public Inasistencia() {
    }

    public Inasistencia(Integer idInasistencia, LocalDate fechaInasistencia, String motivo,
                        Aprendiz aprendiz, Instructor instructor) {
        this.idInasistencia = idInasistencia;
        this.fechaInasistencia = fechaInasistencia;
        this.motivo = motivo;
        this.aprendiz = aprendiz;
        this.instructor = instructor;
    }

    public Integer getIdInasistencia() {
        return idInasistencia;
    }

    public void setIdInasistencia(Integer idInasistencia) {
        this.idInasistencia = idInasistencia;
    }

    public LocalDate getFechaInasistencia() {
        return fechaInasistencia;
    }

    public void setFechaInasistencia(LocalDate fechaInasistencia) {
        this.fechaInasistencia = fechaInasistencia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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
