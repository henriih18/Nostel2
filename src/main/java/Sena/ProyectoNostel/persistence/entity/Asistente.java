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

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private ActividadComplementaria actividadComplementaria;

    /*@ManyToMany(mappedBy = "asistentes")
    private List<Instructor> instructores;*/




}