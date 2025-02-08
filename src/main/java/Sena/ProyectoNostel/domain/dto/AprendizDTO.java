package Sena.ProyectoNostel.domain.dto;
import Sena.ProyectoNostel.persistence.entity.GeneroAprendiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AprendizDTO {
    private Integer idAprendiz;

    //@NotBlank
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private GeneroAprendiz genero;
    //@Email
    private String correo;
    private String telefono;
    private String residencia;
    /*private Boolean discapacidad;*/
    private String grupoEtnico;
    private List<ComentarioDTO> comentarios;
    private List<InasistenciaDTO> inasistencias;
    private List<ActividadComplementariaDTO> actividadComplementarias;
    private  List<PlanMejoramientoDTO> planMejoramientos;

    // Constructores
    public AprendizDTO() {}

    public AprendizDTO(Integer idAprendiz, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, LocalDate fechaNacimiento, GeneroAprendiz genero, String correo, String telefono, String residencia, String grupoEtnico, List<ComentarioDTO> comentarios, List<InasistenciaDTO> inasistencias, List<ActividadComplementariaDTO> actividadComplementarias, List<PlanMejoramientoDTO> planMejoramientos) {
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
        this.comentarios = comentarios;
        this.inasistencias = inasistencias;
        this.actividadComplementarias = actividadComplementarias;
        this.planMejoramientos = planMejoramientos;
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

    /*public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }*/

    public String getGrupoEtnico() {
        return grupoEtnico;
    }

    public void setGrupoEtnico(String grupoEtnico) {
        this.grupoEtnico = grupoEtnico;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }


    public List<InasistenciaDTO> getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(List<InasistenciaDTO> inasistencias) {
        this.inasistencias = inasistencias;
    }

    public List<ActividadComplementariaDTO> getActividadComplementarias() {
        return actividadComplementarias;
    }

    public void setActividadComplementarias(List<ActividadComplementariaDTO> actividadComplementarias) {
        this.actividadComplementarias = actividadComplementarias;
    }

    public List<PlanMejoramientoDTO> getPlanMejoramientos() {
        return planMejoramientos;
    }

    public void setPlanMejoramientos(List<PlanMejoramientoDTO> planMejoramientos) {
        this.planMejoramientos = planMejoramientos;
    }
}
