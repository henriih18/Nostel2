package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "compromisos")
public class Compromiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compromiso")
    private Integer idCompromiso;

    @Column(name = "actividad_decision", nullable = false)
    private String actividadDecision;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "responsable", nullable = false)
    private String responsable;

    @Column(name = "firma_participacion", nullable = false)
    private String firmaParticipacion;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private ActividadComplementaria actividadComplementaria;
}