package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer idComentario;


    @Column(name = "fecha")
    private LocalDate fechaComentario;

    private String comentario;


    //relacion aprendiz
    @ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    @JsonBackReference
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

    @Transient
    private String nombreInstructor;

    @PostLoad
    private void postLoad() {
        if (instructor != null) {
            this.nombreInstructor = instructor.getNombres()  + " " + instructor.getApellidos();
        }
    }

    /*
    public Comentario() {
    }

    public Comentario(Integer idComentario, LocalDate fechaComentario, String comentario, Aprendiz aprendiz, Integer idAprendiz, Instructor instructor, Integer idInstructor, String nombreInstructor) {
        this.idComentario = idComentario;
        this.fechaComentario = fechaComentario;
        this.comentario = comentario;
        this.aprendiz = aprendiz;
        this.idAprendiz = idAprendiz;
        this.instructor = instructor;
        this.idInstructor = idInstructor;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public LocalDate getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDate fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Aprendiz getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public Integer getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Integer idAprendiz) {
        this.idAprendiz = idAprendiz;
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
    }*/

}
