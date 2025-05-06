package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanMejoramientoDTO {
    private Integer idPlanMejoramiento;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private PlanMejoramiento.EstadoPlanMejoramiento estado;
    private String nombreInstructor;

    /*
    public PlanMejoramientoDTO() {

    }

    public PlanMejoramientoDTO(Integer idPlanMejoramiento, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, EstadoPlanMejoramiento estado, String nombreInstructor) {
        this.idPlanMejoramiento = idPlanMejoramiento;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdPlanMejoramiento() {
        return idPlanMejoramiento;
    }

    public void setIdPlanMejoramiento(Integer idPlanMejoramiento) {
        this.idPlanMejoramiento = idPlanMejoramiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoPlanMejoramiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlanMejoramiento estado) {
        this.estado = estado;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }*/
}
