package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

@Data
public class AsistenteDTO {
    private Integer idAsistente;
    private String nombre;
    private String dependenciaEmpresa;
    private String aprueba;
    private String observacion;
    private String firmaParticipacion;

}
