package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ComentarioDTO {

    private Integer idComentario;
    private LocalDate fechaComentario;
    private String comentario;
    private String nombreInstructor;
    private Integer idInstructor;
    private Integer idAprendiz;


}
