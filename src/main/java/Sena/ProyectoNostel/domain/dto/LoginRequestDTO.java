package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String correo;
    private String contrasena;
}
