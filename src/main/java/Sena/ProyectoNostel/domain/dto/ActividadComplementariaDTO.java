package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ActividadComplementariaDTO {
    private String nombreInstructor;
    private Integer idActividad;
    private Integer idAprendiz;
    private Integer idInstructor;
    private String estado; // Enum como String para transferencia
    private String actaNumber;
    private String nombreComite;
    private String ciudad;
    private LocalDate fecha;
    private String horaInicio;
    private String horaFin;
    private String lugarEnlace;
    private String direccionRegionalCentro;
    private String agenda;
    private String objetivos;
    private String desarrollo;
    private String conclusiones;

    // Listas para compromisos y asistentes
    private List<CompromisoDTO> compromisos;
    private List<AsistenteDTO> asistentes;

    // DTO anidados
    @Data
    public static class CompromisoDTO {
        private Integer idCompromiso;
        private String actividadDecision;
        private LocalDate fecha;
        private String responsable;
        private String firmaParticipacion;
    }

    @Data
    public static class AsistenteDTO {
        private Integer idAsistente;
        private String nombre;
        private String dependenciaEmpresa;
        private String aprueba;
        private String observacion;
        private String firmaParticipacion;
    }

}
