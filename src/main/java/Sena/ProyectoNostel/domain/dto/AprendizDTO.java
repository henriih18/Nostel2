package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.GeneroAprendiz;
import Sena.ProyectoNostel.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AprendizDTO {

    //@JsonView(Views.AprendizView.class)
    private Integer idAprendiz;

    private Integer documento;
    //@NotBlank
    //@JsonView(Views.AprendizView.class)
    private String primerNombre;
    //@JsonView(Views.AprendizView.class)
    private String segundoNombre;
    //@JsonView(Views.AprendizView.class)
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private GeneroAprendiz genero;
    //@Email
    private String correo;
    private String contrasena;
    private String telefono;
    private String residencia;
    /*private Boolean discapacidad;*/
    private String grupoEtnico;
    private Integer numeroFicha;
    private String nombrePrograma;
    private Integer numeroAmbiente;
    private List<ComentarioDTO> comentarios;
    private List<InasistenciaDTO> inasistencias;
    private List<ActividadComplementariaDTO> actividadComplementarias;
    private List<PlanMejoramientoDTO> planMejoramientos;

}
