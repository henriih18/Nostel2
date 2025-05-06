package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.ActividadComplementariaDTO;
import Sena.ProyectoNostel.persistence.entity.ActividadComplementaria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadComplementariaMapper {

    @Mapping(source = "instructor.nombres", target = "nombreInstructor")
    ActividadComplementariaDTO toActividadComplementariaDTO(ActividadComplementaria actividadComplementaria);

    List<ActividadComplementariaDTO> toActividadComplementariaList(List<ActividadComplementaria> actividadComplementarias);


    ActividadComplementaria toActividadComplementaria(ActividadComplementariaDTO actividadComplementariaDTO);

    void updateActividadComplementariaFromDto(ActividadComplementariaDTO actividadComplementariaDTO, @MappingTarget ActividadComplementaria actividadComplementaria);

}
