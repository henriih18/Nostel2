package Sena.ProyectoNostel.domain.dto;

public class InstructorDTO {
    private Integer idInstructor;
    private String nombres;
    private String apellidos;
    private Integer numeroDocente;
    private String area;


    public InstructorDTO() {
    }

    public InstructorDTO(Integer idInstructor, String nombres, String apellidos, Integer numeroDocente, String area) {
        this.idInstructor = idInstructor;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.numeroDocente = numeroDocente;
        this.area = area;
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
}
