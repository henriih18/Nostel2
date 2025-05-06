package Sena.ProyectoNostel.persistence.mapper;

import Sena.ProyectoNostel.domain.dto.PlanMejoramientoDTO;
import Sena.ProyectoNostel.persistence.entity.PlanMejoramiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlanMejoramientoMapper {

    @Mapping(source = "instructor.nombres", target = "nombreInstructor")
    PlanMejoramientoDTO toPlanMejoramientoDTO(PlanMejoramiento planMejoramiento);

   List<PlanMejoramientoDTO> toPlanMejoramientoList(List<PlanMejoramiento> planMejoramientos);


   PlanMejoramiento toPlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO);

    void updatePlanMejoramiento(PlanMejoramientoDTO planMejoramientoDTO, @MappingTarget PlanMejoramiento planMejoramiento );
}
