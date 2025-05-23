package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CompromisoPlanDTO {
    private Integer idCompromiso;
    private String planDecision;
    private LocalDate fecha;
    private String responsable;
    private String firmaParticipacion;
}
