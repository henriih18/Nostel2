package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "asistentes_plan")
public class AsistentePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistente")
    private Integer idAsistente;

    private String nombre;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    private  Boolean planta;

    private Boolean contratista;

    private String otro;

    @Column(name = "dependencia_empresa", nullable = false)
    private String dependenciaEmpresa;

    @Column(name = "correo_electronico", nullable = false)
    private String correoElectronico;

    @Column(name = "telefono_ext", nullable = false)
    private String telefonoExt;

    @Column(name = "autoriza_grabacion", nullable = false)
    private Boolean autorizaGrabacion;

    @Column(name = "firma_participacion")
    private String firmaParticipacion;

    private String observacion;

    private String aprueba;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan")
    private PlanMejoramiento planMejoramiento;
}
