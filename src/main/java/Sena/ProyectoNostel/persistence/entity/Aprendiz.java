package Sena.ProyectoNostel.persistence.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aprendices")
public class Aprendiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aprendiz")
    private Integer idAprendiz;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GeneroAprendiz genero;

    //@Email
    private String correo;

    private String telefono;

    private String residencia;

    /*@Column(name = "discapacidad", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean discapacidad = false;*/

    /*@Column(name = "discapacidad")
    private String discapacidad;*/
    //Revisar si es necesario cambiar el tipo de dato de discapacidad


    @Column(name = "grupo_etnico")
    private String grupoEtnico;

    //Relacio entre entidades
    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Inasistencia> inasistencias = new ArrayList<>();

    @OneToMany(mappedBy = "aprendiz")
    private List<PlanMejoramiento> planesMejoramiento;

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ActividadComplementaria> actividadComplementarias;

    @OneToMany(mappedBy = "aprendiz", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comentario> comentarios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_ficha")
    private Ficha ficha;



    public Aprendiz() {
    }

    public Aprendiz(Integer idAprendiz, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, LocalDate fechaNacimiento, GeneroAprendiz genero, String correo, String telefono, String residencia, String grupoEtnico, List<Inasistencia> inasistencias, List<PlanMejoramiento> planesMejoramiento, List<ActividadComplementaria> actividadComplementarias, List<Comentario> comentarios, Ficha ficha) {
        this.idAprendiz = idAprendiz;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.correo = correo;
        this.telefono = telefono;
        this.residencia = residencia;
        this.grupoEtnico = grupoEtnico;
        this.inasistencias = inasistencias;
        this.planesMejoramiento = planesMejoramiento;
        this.actividadComplementarias = actividadComplementarias;
        this.comentarios = comentarios;
        this.ficha = ficha;
    }

    public Integer getIdAprendiz() {
        return idAprendiz;
    }

    public void setIdAprendiz(Integer idAprendiz) {
        this.idAprendiz = idAprendiz;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public GeneroAprendiz getGenero() {
        return genero;
    }

    public void setGenero(GeneroAprendiz genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public List<Inasistencia> getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(List<Inasistencia> inasistencias) {
        this.inasistencias = inasistencias;
    }

    public List<PlanMejoramiento> getPlanesMejoramiento() {
        return planesMejoramiento;
    }

    public void setPlanesMejoramiento(List<PlanMejoramiento> planesMejoramiento) {
        this.planesMejoramiento = planesMejoramiento;
    }

    public List<ActividadComplementaria> getActividadComplementarias() {
        return actividadComplementarias;
    }

    public void setActividadComplementarias(List<ActividadComplementaria> actividadComplementarias) {
        this.actividadComplementarias = actividadComplementarias;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }
}
