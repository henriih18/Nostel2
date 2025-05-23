package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

@Data
public class AsistentePlanDTO {
    private Integer idAsistente;
    private String nombre;
    private String dependenciaEmpresa;
    private String aprueba;
    private String observacion;
    private String firmaParticipacion;
    private String numeroDocumento;
    private Boolean planta;
    private Boolean contratista;
    private String otro;
    private String correoElectronico;
    private String telefonoExt;
    private Boolean autorizaGrabacion;
}
