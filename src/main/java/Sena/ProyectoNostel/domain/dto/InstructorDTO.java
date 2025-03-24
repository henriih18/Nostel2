package Sena.ProyectoNostel.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstructorDTO {
    private Integer idInstructor;
    
    @NotBlank(message = "El campo nombres no puede estar vacío")
    private String nombres;
    
    @NotBlank(message = "El campo apellidos no puede estar vacío")
    private String apellidos;
    
    @NotNull(message = "El campo numeroDocente no puede ser nulo")
    private Integer numeroDocente;
    
    @NotBlank(message = "El campo area no puede estar vacío")
    private String area;
    
    @NotBlank(message = "El campo correo no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String correo;
    
    @NotBlank(message = "El campo contraseña no puede estar vacío")
    private String contrasena;


/*
    public InstructorDTO() {
    }

    public InstructorDTO(Integer idInstructor, String nombres, String apellidos, Integer numeroDocente, String area,  String correo, String contrasena) {
        this.idInstructor = idInstructor;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroDocente = numeroDocente;
        this.area = area;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Integer getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Integer idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getNumeroDocente() {
        return numeroDocente;
    }

    public void setNumeroDocente(Integer numeroDocente) {
        this.numeroDocente = numeroDocente;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }*/
}
