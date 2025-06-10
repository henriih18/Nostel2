package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "compromisos_plan")
public class CompromisoPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compromiso")
    private Integer idCompromiso;

    @Column(name = "plan_decision", nullable = false )
    private String planDecision;

    private LocalDate fecha;

    private String responsable;

    @Column(name = "firma_Participacion", nullable = false )
    private String firmaParticipacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan")
    private PlanMejoramiento planMejoramiento;
}
