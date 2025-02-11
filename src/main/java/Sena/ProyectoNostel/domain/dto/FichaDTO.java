package Sena.ProyectoNostel.domain.dto;

import java.time.LocalDate;
import java.util.List;

public class FichaDTO {

    //@JsonView(Views.FichaView.class)
    private Integer idFicha;

    //@JsonView(Views.FichaView.class)
    private Integer numeroFicha;
   // @JsonView(Views.FichaView.class)
    private String nombrePrograma;
   // @JsonView(Views.FichaView.class)
    private String horario;
    //@JsonView(Views.FichaView.class)
    private LocalDate fechaInicio;
    //@JsonView(Views.FichaView.class)
    private LocalDate fechaFin;
    //@JsonView(Views.FichaView.class)
    private Integer numeroAmbiente;

    private Integer totalAprendices;

    //@JsonView(Views.FichaView.class)
    //private List<AprendizDTO> aprendices;

    public FichaDTO() {

    }

    public FichaDTO(Integer idFicha, Integer numeroFicha, String nombrePrograma, String horario, LocalDate fechaInicio, LocalDate fechaFin,
                    Integer numeroAmbiente, List<AprendizDTO> aprendices, Integer totalAprendices) {
        this.idFicha = idFicha;
        this.numeroFicha = numeroFicha;
        this.nombrePrograma = nombrePrograma;
        this.horario = horario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroAmbiente = numeroAmbiente;
        //this.aprendices = aprendices;
        this.totalAprendices = totalAprendices;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public Integer getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(Integer numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getNumeroAmbiente() {
        return numeroAmbiente;
    }

    public void setNumeroAmbiente(Integer numeroAmbiente) {
        this.numeroAmbiente = numeroAmbiente;
    }

    /*public List<AprendizDTO> getAprendices() {
        return aprendices;
    }

    public void setAprendices(List<AprendizDTO> aprendices) {
        this.aprendices = aprendices;
    }*/

    public Integer getTotalAprendices() {
        return totalAprendices;
    }

    public void setTotalAprendices(Integer totalAprendices) {
        this.totalAprendices = totalAprendices;
    }
}
