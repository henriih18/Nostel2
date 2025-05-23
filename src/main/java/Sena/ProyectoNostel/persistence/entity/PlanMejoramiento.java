package Sena.ProyectoNostel.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "planes_mejoramiento")
public class PlanMejoramiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlanMejoramiento;

    @ManyToOne
    @JoinColumn(name = "id_aprendiz", insertable = false, updatable = false)
    private Aprendiz aprendiz;
    @Column(name = "id_aprendiz")
    private Integer idAprendiz;

    //relacion instructor
    @ManyToOne
    @JoinColumn(name = "id_instructor", insertable = false, updatable = false)
    @JsonBackReference
    private Instructor instructor;
    @Column(name = "id_instructor")
    private Integer idInstructor;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private PlanMejoramiento.EstadoPlan estado = PlanMejoramiento.EstadoPlan.Pendiente;

    @Column(name = "acta_number", nullable = false)
    private String actaNumber;

    @Column(name = "nombre_comite", nullable = false)
    private String nombreComite;

    private String ciudad;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private String horaFin;

    @Column(name = "lugar_enlace", nullable = false)
    private String lugarEnlace;

    @Column(name = "direccion_regional_centro", nullable = false)
    private String direccionRegionalCentro;

    @Column(name = "agenda", nullable = false, columnDefinition = "TEXT")
    private String agenda;

    @Column(name = "objetivos", nullable = false, columnDefinition = "TEXT")
    private String objetivos;

    @Column(name = "desarrollo", nullable = false, columnDefinition = "TEXT")
    private String desarrollo;

    @Column(name = "conclusiones", nullable = false, columnDefinition = "TEXT")
    private String conclusiones;

    // Relación con Compromisos
    @OneToMany(mappedBy = "planMejoramiento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CompromisoPlan> compromisosPlan;

    @OneToMany(mappedBy = "planMejoramiento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AsistentePlan> asistentesPlan;


    public enum EstadoPlan {
        Pendiente, Entregado, Calificado
    }

}
