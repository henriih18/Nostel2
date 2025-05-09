package Sena.ProyectoNostel.persistence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "instructores")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructor")
    private Integer idInstructor;

    private String nombres;

    private String apellidos;

    @Column(name = "numero_docente")
    private Integer numeroDocente;

    private String area;

    private String correo;

    @OneToMany(mappedBy = "instructor")
    private List<ActividadComplementaria> actividadComplementarias;

    @OneToMany(mappedBy = "instructor")
    private List<PlanMejoramiento> planesMejoramiento;

    @OneToMany(mappedBy = "instructor")
    @JsonManagedReference
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "instructor")
    private List<FichasInstructor> fichasInstructores;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    /*@ManyToMany
    @JoinTable(
            name = "instructor_asistente",
            joinColumns = @JoinColumn(name = "id_instructor"),
            inverseJoinColumns = @JoinColumn(name = "id_asistente")
    )
    private List<Asistente> asistentes;*/


}
