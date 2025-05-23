package Sena.ProyectoNostel.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "asistentes")
public class Asistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistente")
    private Integer idAsistente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "dependencia_empresa", nullable = false)
    private String dependenciaEmpresa;

    @Column(name = "aprueba", nullable = false)
    private String aprueba;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "firma_participacion", nullable = false)
    private String firmaParticipacion;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    private Boolean planta;

    private Boolean contratista;

    private String otro;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "telefono_ext")
    private String telefonoExt;

    @Column(name = "autoriza_grabacion")
    private Boolean autorizaGrabacion;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private ActividadComplementaria actividadComplementaria;





}