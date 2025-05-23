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
}
