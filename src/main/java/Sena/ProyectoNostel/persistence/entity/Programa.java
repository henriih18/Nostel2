package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "programas")
public class Programa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_programa")
    private Integer idPrograma;

    @Column(name = "nombre_programa")
    private String nombrePrograma;

    @OneToMany(mappedBy = "programa")
    private List<Ficha> fichas;

    //IMPELMETAR NIVELES DE FORMACION - TAMBIEN EN BD

    public Programa() {
    }

    public Programa(Integer idPrograma, String nombrePrograma, List<Ficha> fichas) {
        this.idPrograma = idPrograma;
        this.nombrePrograma = nombrePrograma;
        this.fichas = fichas;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(List<Ficha> fichas) {
        this.fichas = fichas;
    }
}
