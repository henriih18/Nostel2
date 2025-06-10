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

    @Column(name = "id_aprendiz")
    private Integer idAprendiz;

    //relacion aprendiz
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    @JsonBackReference
    private Aprendiz aprendiz;

    @Column(name = "id_instructor")
    private Integer idInstructor;

    //relacion instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    @JsonBackReference
    private Instructor instructor;


    /*@Transient
    private String nombreInstructor;*/

}
