package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ComentarioDTO {

    private Integer idComentario;
    private LocalDate fechaComentario;
    private String comentario;
    private String nombreInstructor;

    /*public ComentarioDTO() {
    }

    public ComentarioDTO(Integer idComentario, LocalDate fechaComentario, String comentario, String nombreInstructor) {
        this.idComentario = idComentario;
        this.fechaComentario = fechaComentario;
        this.comentario = comentario;
        this.nombreInstructor = nombreInstructor;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public LocalDate getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDate fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }*/
}
