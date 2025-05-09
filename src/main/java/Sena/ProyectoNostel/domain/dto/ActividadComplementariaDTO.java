package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ActividadComplementariaDTO {
    //private String nombreInstructor;
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




}
