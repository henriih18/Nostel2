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
    private Integer idAprendiz;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, unique = true)
    private Integer documento;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private GeneroAprendiz genero;

    @Column(nullable = false, unique = true)
    private String correo;

    /*@Column(nullable = false)
    private String contrasena;*/

    private String telefono;

    private String residencia;



    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ficha", insertable = false, updatable = false)
    @JsonBackReference
    private Ficha ficha;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonBackReference
    private Usuario usuario;



    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<PlanMejoramiento> planMejoramientos = new ArrayList<>();

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActividadComplementaria> actividadComplementarias = new ArrayList<>();

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comentario> comentarios = new ArrayList<>();

    public enum GeneroAprendiz {
        Masculino,
        Femenino,
        Otro
    }

    public enum TipoDocumento {
        CEDULA_CIUDADANIA,
        TARJETA_IDENTIDAD,
        CEDULA_EXTRANJERIA,
        PASAPORTE
    }

}
