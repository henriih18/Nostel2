package Sena.ProyectoNostel.domain.dto;

import java.time.LocalDate;
import java.util.List;

public class InasistenciaDTO {
    private Integer idInasistencia;
    private LocalDate fechaInasistencia;
    private String motivo;
    private String nombreInstructor;

   public InasistenciaDTO() {
   }

    public InasistenciaDTO(Integer idInasistencia, LocalDate fechaInasistencia, String motivo, String nombreInstructor) {
        this.idInasistencia = idInasistencia;
        this.fechaInasistencia = fechaInasistencia;
        this.motivo = motivo;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdInasistencia() {
        return idInasistencia;
    }

    public void setIdInasistencia(Integer idInasistencia) {
        this.idInasistencia = idInasistencia;
    }

    public LocalDate getFechaInasistencia() {
        return fechaInasistencia;
    }

    public void setFechaInasistencia(LocalDate fechaInasistencia) {
        this.fechaInasistencia = fechaInasistencia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }
}
