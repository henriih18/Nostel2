package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.EstadoActvidad;

import java.time.LocalDate;

public class ActividadComplementariaDTO {
    private Integer idActividad;
    private String actividad;
    private LocalDate fechaAsignacion;
    private LocalDate fechaEntrega;
    private EstadoActvidad estado;
    private String nombreInstructor;

    public ActividadComplementariaDTO() {

    }

    public ActividadComplementariaDTO(Integer idActividad, String actividad, LocalDate fechaAsignacion, LocalDate fechaEntrega, EstadoActvidad estado, String nombreInstructor) {
        this.idActividad = idActividad;
        this.actividad = actividad;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoActvidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActvidad estado) {
        this.estado = estado;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }
}
