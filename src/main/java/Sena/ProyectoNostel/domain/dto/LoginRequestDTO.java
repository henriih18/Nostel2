package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String correo;
    private String contrasena;
    private String rol;
    private Integer idFicha;
}
