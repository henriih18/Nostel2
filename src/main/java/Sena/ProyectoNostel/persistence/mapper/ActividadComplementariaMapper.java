package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadComplementariaMapper {

    @Mapping(source = "actividad", target = "actividad")
    @Mapping(source = "fechaAsignacion", target = "fechaAsignacion")
    @Mapping(source = "fechaEntrega", target = "fechaEntrega")
    @Mapping(source = "estado", target = "estado")
   // @Mapping(target = "aprendiz", ignore = true)

    @Mapping(expression = "java(actividadComplementaria.getInstructor().getNombres() + \" \" + actividadComplementaria.getInstructor().getApellidos())", target = "nombreInstructor")
    ActividadComplementariaDTO toActividadCompelemtariaDTO(ActividadComplementaria actividadComplementaria);

    List<ActividadComplementariaDTO> toActividadComplementariaList(List<ActividadComplementaria> actividadComplementarias);


    @Mapping(source = "actividad", target = "actividad")
    @Mapping(source = "fechaAsignacion", target = "fechaAsignacion")
    @Mapping(source = "fechaEntrega", target = "fechaEntrega")
    @Mapping(source = "estado", target = "estado")
    //@Mapping(target = "idInstructor", ignore = true)
    //@Mapping(target = "idAprendiz", ignore = true)
    //@Mapping(target = "aprendiz", ignore = true)

    ActividadComplementaria toActividadComplementaria(ActividadComplementariaDTO actividadComplementariaDTO);

    void updateActividadComplementariaFromDto(ActividadComplementariaDTO actividadComplementariaDTO, @MappingTarget ActividadComplementaria actividadComplementaria);

}
