package Sena.ProyectoNostel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String rol;
    private String correo;
    private String nombres;
}
