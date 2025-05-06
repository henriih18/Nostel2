package Sena.ProyectoNostel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String rol;
    /*private Integer idFicha;
    private String nombre;*/
    private Integer idUsuario;


}
