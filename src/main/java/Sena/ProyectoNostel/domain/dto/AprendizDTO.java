/*
package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.GeneroAprendiz;
import Sena.ProyectoNostel.persistence.entity.TipoDocumento;
import Sena.ProyectoNostel.util.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AprendizDTO {

    //@JsonView(Views.AprendizView.class)
    private Integer idAprendiz;

    private TipoDocumento tipoDocumento;
    private Integer documento;

    //@JsonView(Views.AprendizView.class)
    private String nombres;

    //@JsonView(Views.AprendizView.class)
    private String apellidos;

    private LocalDate fechaNacimiento;
    private GeneroAprendiz genero;
    @Email
    private String correo;
    private String contrasena;
    private String telefono;
    private String residencia;

    private Integer numeroFicha;
    private String nombrePrograma;
    private Integer numeroAmbiente;
    private List<ComentarioDTO> comentarios;

    private List<ActividadComplementariaDTO> actividadComplementarias;
    private List<PlanMejoramientoDTO> planMejoramientos;

}
*/

package Sena.ProyectoNostel.domain.dto;

import Sena.ProyectoNostel.persistence.entity.Aprendiz;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AprendizDTO {

    private Integer idAprendiz;

    @NotNull(message = "El tipo de documento es obligatorio")
    private Aprendiz.TipoDocumento tipoDocumento;

    @NotNull(message = "El documento es obligatorio")
    @Min(value = 6, message = "El documento debe tener al menos 6 dígitos")
    private Integer documento;

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombres;

    @NotNull(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    private Aprendiz.GeneroAprendiz genero;

    @Email(message = "El correo debe ser válido")
    @NotNull(message = "El correo es obligatorio")
    private String correo;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$",
            message = "La contraseña debe tener al menos una mayúscula, una minúscula, un número y un carácter especial"
    )
    private String contrasena;

    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Telefono invalido")
    private String telefono;

    @NotNull(message = "La residencia es obligatoria")
    @Size(max = 100, message = "La residencia debe tener máximo 100 caracteres")
    private String residencia;

    private Integer numeroFicha;
    private String nombrePrograma;
    private Integer numeroAmbiente;
    //private List<ComentarioDTO> comentarios;

    //private List<ActividadComplementariaDTO> actividadComplementarias;
    //private List<PlanMejoramientoDTO> planMejoramientos;

    private Integer totalActividades;
    private Integer totalPlanes;
    private Integer totalComentarios;

}

