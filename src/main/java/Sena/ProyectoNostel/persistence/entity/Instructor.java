package Sena.ProyectoNostel.persistence.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany(mappedBy = "instructor")
    private List<ActividadComplementaria> actividadComplementarias;

    @OneToMany(mappedBy = "instructor")
    private List<Inasistencia> inasistencias;

    @OneToMany(mappedBy = "instructor")
    private List<PlanMejoramiento> planesMejoramiento;

    @OneToMany(mappedBy = "instructor")
    @JsonManagedReference
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "instructor")
    private List<FichasInstructor> fichasInstructores;

    public Instructor() {
    }

    public Instructor(Integer idInstructor, String nombres, String apellidos, Integer numeroDocente,
                      String area, List<ActividadComplementaria> actividadesComplementaria,
                      List<Inasistencia> inasistencias, List<PlanMejoramiento> planesMejoramiento,
                      List<Comentario> comentarios, List<FichasInstructor> fichasInstructores) {
        this.idInstructor = idInstructor;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroDocente = numeroDocente;
        this.area = area;
        this.actividadComplementarias = actividadesComplementaria;
        this.inasistencias = inasistencias;
        this.planesMejoramiento = planesMejoramiento;
        this.comentarios = comentarios;
        this.fichasInstructores = fichasInstructores;
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getNumeroDocente() {
        return numeroDocente;
    }

    public void setNumeroDocente(Integer numeroDocente) {
        this.numeroDocente = numeroDocente;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<ActividadComplementaria> getActividadComplementarias() {
        return actividadComplementarias;
    }

    public void setActividadComplementarias(List<ActividadComplementaria> actividadComplementarias) {
        this.actividadComplementarias = actividadComplementarias;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<FichasInstructor> getFichasInstructores() {
        return fichasInstructores;
    }

    public void setFichasInstructores(List<FichasInstructor> fichasInstructores) {
        this.fichasInstructores = fichasInstructores;
    }
}
