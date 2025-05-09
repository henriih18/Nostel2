package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CompromisoDTO {
    private Integer idCompromiso;
    private String actividadDecision;
    private LocalDate fecha;
    private String responsable;
    private String firmaParticipacion;
}
