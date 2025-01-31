package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

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
    private String fechaInicio;

    @Column(name = "fecha_fin")
    private String fechaFin;

    @Column(name = "numero_ambiente")
    private String numeroAmbiente;

    //FALTA EL ID-PROGRAMA
    @ManyToOne
    @JoinColumn(name = "id_programa", insertable = false, updatable = false)
    private Programa programa;

    @OneToMany(mappedBy = "ficha")
    private List<FichasInstructor> fichasInstructores;

    @OneToMany(mappedBy = "ficha")
    private List<Aprendiz> aprendices;



    public Ficha() {
    }

    public Ficha(Integer idFicha, Integer numeroFicha, String nombrePrograma, String horario,
                 String fechaInicio, String fechaFin,
                 String numeroAmbiente, Programa programa, List<FichasInstructor> fichasInstructores) {
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNumeroAmbiente() {
        return numeroAmbiente;
    }

    public void setNumeroAmbiente(String numeroAmbiente) {
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
}
