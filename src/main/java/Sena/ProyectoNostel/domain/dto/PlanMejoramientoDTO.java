package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PlanMejoramientoDTO {
    private Integer idPlanMejoramiento;
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
    private List<CompromisoPlanDTO> compromisosPlan;
    private List<AsistentePlanDTO> asistentesPlan;
}
