package Sena.ProyectoNostel.persistence.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "aprendices")
public class Aprendiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprendiz")
    private Integer idAprendiz;

    private Integer documento;

   // @Column(name = "primer_nombre")
    private String nombres;

   /* @Column(name = "segundo_nombre")
    private String segundoNombre;*/

    //@Column(name = "primer_apellido")
    private String apellidos;

    /*@Column(name = "segundo_apellido")
    private String segundoApellido;*/



    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GeneroAprendiz genero;

    //@Email
    private String correo;

    private String contrasena;

    private String telefono;

    private String residencia;

    /*@Column(name = "discapacidad", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean discapacidad = false;*/

    /*@Column(name = "discapacidad")
    private String discapacidad;*/
    //Revisar si es necesario cambiar el tipo de dato de discapacidad


    /*@Column(name = "grupo_etnico")
    private String grupoEtnico;*/

    @ManyToOne
    @JoinColumn(name = "id_ficha", insertable = false, updatable = false)
    @JsonBackReference
    private Ficha ficha;
    /*@Column(name = "id_ficha")
    private Integer idFicha;

    @Transient
    private Integer numeroFicha;

    @PostLoad
    private void postLoad() {
        if(ficha != null) {
            this.numeroFicha = ficha.getNumeroAmbiente();
        }
    }*/

    //Relacio entre entidades
    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Inasistencia> inasistencias = new ArrayList<>();

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PlanMejoramiento> planMejoramientos = new ArrayList<>();

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActividadComplementaria> actividadComplementarias;

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comentario> comentarios = new ArrayList<>();


}
