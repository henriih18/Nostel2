package Sena.ProyectoNostel.domain.dto;

import lombok.Data;

@Data
public class InstructorDTO {
    private Integer idInstructor;
    private String nombres;
    private String apellidos;
    private Integer numeroDocente;
    private String area;
    private String correo;
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
