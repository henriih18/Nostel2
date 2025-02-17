package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "fichas")
public class Ficha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ficha")
    private Integer idFicha;

    @Column(name = "numero_ficha")
    private Integer numeroFicha;

    @Column(name = "nombre_programa")
    private String nombrePrograma;

    private String horario;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "numero_ambiente")
    private Integer numeroAmbiente;

    //FALTA EL ID-PROGRAMA
    @ManyToOne
    @JoinColumn(name = "id_programa", insertable = false, updatable = false)
    private Programa programa;

    @OneToMany(mappedBy = "ficha")
    private List<FichasInstructor> fichasInstructores;

    @OneToMany(mappedBy = "ficha", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Aprendiz> aprendices = new ArrayList<>();

/*

    public Ficha() {
    }

    public Ficha(Integer idFicha, Integer numeroFicha, String nombrePrograma, String horario,
                 LocalDate fechaInicio, LocalDate fechaFin,
                 Integer numeroAmbiente, Programa programa, List<FichasInstructor> fichasInstructores) {
        this.idFicha = idFicha;
        this.numeroFicha = numeroFicha;
        this.nombrePrograma = nombrePrograma;
        this.horario = horario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroAmbiente = numeroAmbiente;
        this.programa = programa;
        this.fichasInstructores = fichasInstructores;
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

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public List<FichasInstructor> getFichasInstructores() {
        return fichasInstructores;
    }

    public void setFichasInstructores(List<FichasInstructor> fichasInstructores) {
        this.fichasInstructores = fichasInstructores;
    }

    public List<Aprendiz> getAprendices() {
        return aprendices;
    }

    public void setAprendices(List<Aprendiz> aprendices) {
        this.aprendices = aprendices;
    }

 */
}
